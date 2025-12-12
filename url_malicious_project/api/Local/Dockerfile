# Using a base image with Python
FROM python:3.9-slim

# Create a directory for the app
WORKDIR /app

# Copiar los archivos necesarios
COPY mle-intv-main/Application/Local/model_url_malicious.py mle-intv-main/Application/trained_model.joblib  /app/

# Copy the necessary files
RUN pip install pandas joblib Flask
RUN pip install -U scikit-learn 

# Expose port for API
EXPOSE 5000

# Command to run the application
CMD ["python", "model_url_malicious.py"]