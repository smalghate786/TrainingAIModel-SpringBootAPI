# TrainingAIModel-SpringBootAPI
The model processes input data, compares its predictions to the correct answers (labels), and adjusts its internal parameters to reduce errors.
# AI Model Training API
---

## 🚀 API Endpoints

### **1️⃣ Train Model**
**URL:**  
`POST http://localhost:8282/ai/addData`
 `POST http://localhost:8282/ai/train`
 `GET http://localhost:8282/ai/predict?text=i dont like this`


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

