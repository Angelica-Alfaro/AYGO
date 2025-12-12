# Using a base image with Python
FROM public.ecr.aws/lambda/python:3.9

# Create a directory for the app
WORKDIR /app

# Copiar los archivos necesarios

COPY aws/model_url_malicious_lambda.py ${LAMBDA_TASK_ROOT}
COPY trained_model.joblib ${LAMBDA_TASK_ROOT}
COPY vectorizer.joblib ${LAMBDA_TASK_ROOT}

# Copy the necessary file
RUN pip install pandas joblib boto3 --target "${LAMBDA_TASK_ROOT}"
RUN pip install -U scikit-learn --target "${LAMBDA_TASK_ROOT}"

# Command to run the application
CMD ["model_url_malicious_lambda.lambda_handler"]