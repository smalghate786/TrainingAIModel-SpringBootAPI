# TrainingAIModel-SpringBootAPI
The model processes input data, compares its predictions to the correct answers (labels), and adjusts its internal parameters to reduce errors.
# AI Model Training API

This project is a **Spring Boot-based API** for training AI models (e.g., with Deeplearning4j).  
It allows you to:
- Start training a new model with custom parameters
- Check the training progress/status
- Retrieve the job ID for tracking

---

## 📌 Features
- REST API for initiating AI model training
- Customizable training parameters (model name, dataset path, epochs, learning rate)
- Status endpoint to monitor ongoing training
- Structured error handling

---


---

## 🚀 API Endpoints

### **1️⃣ Train Model**
**URL:**  
`POST /api/ai-trainer/train`

**Request Body:**
```json
{
  "modelName": "image-classifier-v1",
  "datasetPath": "/datasets/images",
  "epochs": 10,
  "learningRate": 0.001
}
```
**Response**
```json
{
  "jobId": "b4a3a3e5-7d6d-42d9-95cf-2af3b98b70e7",
  "status": "STARTED",
  "message": "Training initiated successfully"
}
```
Execute in sequence
1. http://localhost:8282/ai/addData
```json
{
    "text": "I love this product",
    "label": 0
}
```

2. http://localhost:8282/ai/addData
```json
{
    "text": "I hate this product",
    "label": 1
}
```
3. http://localhost:8282/ai/train
4. http://localhost:8282/ai/predict?text=i dont like this
5. 

