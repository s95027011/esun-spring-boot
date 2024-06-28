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

- Vue-Router: https://book.vue.tw/CH4/4-1-vue-router-intro.html

- Vuejs: https://vuejs.org/examples/#fetching-data

- Vuejs doenload: https://vuejs.org/guide/quick-start

- 前後端代理設定參考: https://smallbookboy.com/vue%E4%BD%BF%E7%94%A8vite%E8%A8%AD%E5%AE%9A%E4%BB%A3%E7%90%86proxy%EF%BC%8C%E8%BD%89%E7%99%BC%E8%AB%8B%E6%B1%82%E7%B5%A6%E5%BE%8C%E7%AB%AFspring-boot/#vue%e8%a8%ad%e5%ae%9a%e4%bb%a3%e7%90%86

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

         npm create vue@latest

         cd <your-project-name>
         npm install
         npm run dev

`http://localhost:5173/`

### 後端

      mvnw spring-boot:run

`http://localhost:8080/`

## API 測試指令

- 註冊

`POST http://localhost:8080/api/signup`

      {
          "email": "sss@gmail.com",
          "password": "abc123",
          "confirmPassword": "abc123",
          "userName": "Amy",
          "account": "1111"
      }

- 登入

`POST http://localhost:8080/api/login`

      {
          "email": "sss@gmail.com",
          "password": "abc123"
      }

and return

      token: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzc3NAZ21haWwuY29tIiwiaWF0IjoxNzE4OTk2MTY0LCJleHAiOjE3MTg5OTk3NjR9.gyfO7NLeadAogr2Az61TG84Tl9eUweMcnMgcomZzxlU

*之後使用Token --> 於Header中加入*

      Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzc3NAZ21haWwuY29tIiwiaWF0IjoxNzE4OTk2MTY0LCJleHAiOjE3MTg5OTk3NjR9.gyfO7NLeadAogr2Az61TG84Tl9eUweMcnMgcomZzxlU

- 確認目前登入狀態

`GET http://localhost:8080/users/me`

+

      Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzc3NAZ21haWwuY29tIiwiaWF0IjoxNzE4OTk2MTY0LCJleHAiOjE3MTg5OTk3NjR9.gyfO7NLeadAogr2Az61TG84Tl9eUweMcnMgcomZzxlU

## 所有功能都需要帶著Token

- 新增商品

`POST http://localhost:8080/api/products/add`

      {
          "productName": "Investment Fund A",
          "price": 100.0,
          "feeRate": 0.1
      }

- 查看所有商品

`GET http://localhost:8080/api/products/all`

- 新增清單：功能1 新增喜好金融商品

`POST http://localhost:8080/api/like-list/add`

      {
        "userId": 1,
        "account": "user123",
        "productIds": [1, 2, 3]
      }

- 查詢清單：功能2 詢喜好金融商品清單

`GET http://localhost:8080/api/like-list/{likeListId}`

`GET http://localhost:8080/api/like-list/user/{likeListId}`

- 刪除商品：功能3 刪除喜好金融商品資訊，

`DELETE http://localhost:8080/api/like-list/remove-product/{likeListId}/{productId}`

- 變更清單內容：功能4 更改喜好金融商品資訊

`PUT http://localhost:8080/api/like-list/update-products/{likeListId}`

      {
        "userId": 1,
        "account": "user123",
        "productIds": [1, 2, 3]
      }
