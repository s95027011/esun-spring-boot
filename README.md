# esun-spring-boot

- 語言：java version 21.0.1

- 框架：Spring Boot 3.3.1+ Restful API

- 資料庫：MySQL + Spring JPA

- IDE：eclipse

- 專案建立工具：Maven

- API測試工具：talend api tester

## Ref
- 使用者註冊/登入 ref: https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac

- Spring boot ref: https://ithelp.ithome.com.tw/articles/10318938

- JPA: https://ithelp.ithome.com.tw/articles/10326115

- Vuejs: https://book.vue.tw/CH4/4-1-vue-router-intro.html
https://vuejs.org/examples/#fetching-data

## 所需功能

1. 新增喜好金融商品

   使用者可以透過介面進行新增所喜好的金融商品資訊(產品名稱、產品價格、手續費率)、預計要扣款的帳號、購買數量。
3. 查詢喜好金融商品清單

   使用者可以透過介面進行查詢所喜好的金融商品名稱清單以及預計要扣款的帳號、預計扣款總金額、總手續費用、扣款帳號、使用者聯絡電子信箱。
4. 刪除喜好金融商品資訊

   使用者可以透過介面進行刪除所喜好的金融商品資訊(產品名稱、產品價格、手續費率) 。
5. 更改喜好金融商品資訊

   使用者可以透過介面進行更改所喜好的金融商品資訊(產品名稱、產品價格、手續費率)、預計要扣款的帳號、購買數量。
### 前端

`npm install vue-router`

`npm run serve`

`http://localhost:3000/`

### 後端

`mvnw spring-boot:run`

`http://localhost:8080/`

## API 測試指令

- 註冊

`POST http://localhost:8080/auth/signup`

`{
    "email": "sss@gmail.com",
    "password": "abc123",
    "confirmPassword": "abc123",
    "userName": "Amy",
    "account": "1111"
}`

- 登入

`POST http://localhost:8080/auth/login`

`{
    "email": "sss@gmail.com",
    "password": "abc123"
}`

and return

`token: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzc3NAZ21haWwuY29tIiwiaWF0IjoxNzE4OTk2MTY0LCJleHAiOjE3MTg5OTk3NjR9.gyfO7NLeadAogr2Az61TG84Tl9eUweMcnMgcomZzxlU`

*之後使用Token --> 於Header中加入*

`Authorization Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzc3NAZ21haWwuY29tIiwiaWF0IjoxNzE4OTk2MTY0LCJleHAiOjE3MTg5OTk3NjR9.gyfO7NLeadAogr2Az61TG84Tl9eUweMcnMgcomZzxlU`

- 確認目前登入狀態

`GET http://localhost:8080/users/me`

- 新增商品

`POST http://localhost:8080/api/products/add`

`{
    "productName": "Investment Fund A",
    "price": 100.0,
    "feeRate": 0.1
}
`
- 新增清單：功能1 新增喜好金融商品

`POST http://localhost:8080/api/like-list/add`

`{
  "userId": 1,
  "account": "user123",
  "productIds": [1, 2, 3]
}`

- 查詢清單：功能2 詢喜好金融商品清單

`GET http://localhost:8080/api/like-list/1`

`GET http://localhost:8080/api/like-list/user/1`

- 刪除商品：功能3 刪除喜好金融商品資訊

`DELETE http://localhost:8080/api/like-list/remove-product/1/1`

- 變更清單內容：功能4 更改喜好金融商品資訊

`PUT http://localhost:8080/api/like-list/update-products/1`

`{
  "userId": 1,
  "account": "user123",
  "productIds": [1, 2, 3]
}`
