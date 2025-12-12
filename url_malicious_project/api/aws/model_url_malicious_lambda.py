import joblib
import pandas as pd
import boto3
import os
from scipy.sparse import hstack
import re

def get_file_from_s3(bucket_name,file_name):
    # Set up the S3 client
    s3 = boto3.client('s3')
    # Bucket and file details
    bucket_name = bucket_name
    object_key = file_name
    local_file_path = f"/tmp/{object_key}"  # Temporary download

    # Download the file from S3
    s3.download_file(bucket_name, object_key, local_file_path)
    return local_file_path

def split_url_function(url):
  split_url_list = url.split("/")
  while "" in split_url_list:
    split_url_list.remove("")
  domain=split_url_list[1].split(".")
  top_level_domain = domain[-1]
  while "" in domain:
    domain.remove("")
  domain = domain[0:-1]
  punc = []
  quantity_punctuatuion = 0
  quantity_numbers = 0
  for x in range (len(domain)):
    quantity_punctuatuion += len(re.findall(r'[^a-zA-Z0-9]',domain[x]))
    quantity_numbers += len(re.findall(r'[0-9]',domain[x]))
    punc = punc + re.findall(r'[^a-zA-Z]',domain[x])
    domain[x] = re.sub("[^a-zA-Z\\s]", ' ',domain[x])

  quantity_punctuatuion_total = len(punc)
  domain.append(' '.join(punc))
  protocol = re.sub("[^a-zA-Z\\s]", '',split_url_list[0])
  new_data = {
        "protocol": protocol,
        "domain": " ".join(domain),
        "top_level_domain": top_level_domain,
        "size_domain": len(domain),
        "size_url": len(split_url_list[1]),
        "quantity_punctuatuion": quantity_punctuatuion,
        "quantity_punctuatuion_total": quantity_punctuatuion_total,
        "quantity_numbers": quantity_numbers
    }


  return new_data

def lambda_handler(event, context):
    try:
        # Load the saved model
        model = joblib.load(file_model)
        tfidf_vectorizers = joblib.load(file_vectorizer)
        # Preprocesar y transformar todas las URLs
        protocols, domains, top_level_domains, size_domains,size_domains_full = [], [], [], [], []
        quantity_punctuatuions, quantity_punctuatuions_total, quantities_numbers= [], [], []
        # Make predictions
        for url in event:
            new_data = split_url_function(url)
            protocols.append(new_data["protocol"])
            domains.append(new_data["domain"])
            top_level_domains.append(new_data["top_level_domain"])
            size_domains.append(new_data["size_domain"])
            size_domains_full.append(new_data["size_url"])
            quantity_punctuatuions.append(new_data["quantity_punctuatuion"])
            quantity_punctuatuions_total.append(new_data["quantity_punctuatuion_total"])
            quantities_numbers.append(new_data["quantity_numbers"])
        # Transformar columnas de texto con los vectorizadores correspondientes
        protocol_tfidf = tfidf_vectorizers["protocol"].transform(protocols)
        domain_tfidf = tfidf_vectorizers["domain"].transform(domains)
        top_level_domain_tfidf = tfidf_vectorizers["top_level_domain"].transform(top_level_domains)
        # Combinar las transformaciones TF-IDF
        combined_tfidf = hstack([protocol_tfidf, domain_tfidf, top_level_domain_tfidf])
        # Añadir la columnas numericas
        numeric_column = [[size_domain, size_url, quantity_punctuatuion_total] 
                          for size_domain, size_url, quantity_punctuatuion_total 
                          in zip(size_domains, size_domains_full, quantity_punctuatuions_total)]   # Convertir a formato columna
        final_combined_features = hstack([combined_tfidf, numeric_column])


        # Realizar las predicciones
        predictions = model.predict(final_combined_features).tolist()
        predictions_proba = model.predict_proba(final_combined_features).tolist()
        response = []
        for x in range(len(predictions)):
           response.append({"Url":event[x],"Percentages": predictions_proba[x], "Prediction":predictions[x]})

        return response
    
    except Exception as e:
        return {
            "statusCode": 200,
            "body": f"error: {str(e)}"
        }
    
file_model = get_file_from_s3(os.getenv("BUCKET_NAME"),os.getenv("FILE_NAME_MODEL"))
file_vectorizer = get_file_from_s3(os.getenv("BUCKET_NAME"),os.getenv("FILE_NAME_VECTORIZER"))