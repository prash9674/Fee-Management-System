# Fee-Management-System
An automated system for managing student fees, payment tracking, and receipt generation

Sample Request and Response 

POST http://localhost:8080/api/v1/fees/pay

Request

{
  "id": "12345",
  "studentId": "12345",
  "amount": 500.0,
  "paymentMethod": "Card",
  "term": "Summer 2024",
  "status": "PAID",
  "createdAt": "{{$isoTimestamp}}"
}

Response

{
    "id": "12345",
    "studentId": "12345",
    "amount": 500.0,
    "paymentMethod": "Card",
    "term": "Summer 2024",
    "status": "PAID",
    "receiptId": "REC1729521534272",
    "createdAt": "2024-10-21T14:38:54.245Z"
}


GET http://localhost:8080/api/v1/receipts/REC1729521534272

Response

{
    "id": "6716677e9c4e587f72f23f8e",
    "orderId": "REC1729521534272",
    "studentId": "12345",
    "amount": 500.0,
    "status": "PAID",
    "receiptDate": "2024-10-21"
}

