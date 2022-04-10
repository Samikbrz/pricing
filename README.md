## Pricing Application

This application allows creating, listing, updating, adding products to the cart, deleting products and calculating
the products in the cart with discounted prices.

### Technologies

- Spring Boot
- Docker
- Liquibase
- Postgresql

### Requests

- [POST] localhost:8080/api/v1/product
  #### Request Body
    - name
    - discountType [params = BOGO or DISCOUNTLESS]
    - price
---
- [GET] localhost:8080/api/v1/product
---
- [PUT] localhost:8080/api/v1/product/{productId}
  #### Request Body
    - name
    - discountType [params = BOGO or DISCOUNTLESS]
    - price
---
- [POST] localhost:8080/api/v1/cart/addProduct/{productId}
---
- [GET] localhost:8080/api/v1/cart
---
- [GET] localhost:8080/api/v1/cart/deleteProductFromCart/{productId}
