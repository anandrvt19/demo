This is sample Image Uploading and retrieving service using sharding and caching.

How to run?

Its a spring boot application, so run Application(in the package com.demo.imagesharing) program as Java application 

and hit the swager ui at http://localhost:8080/swagger-ui.html#/...It will show these API to use it.

image-sharing-controller : Image Sharing Controller Show/Hide List Operations Expand Operations
GET /api/v1/imagesharing/getAllImages
getAllImages

GET /api/v1/imagesharing/getImageByPhotoURL
getImageByPhotoId

GET /api/v1/imagesharing/getImageMetaDataByPhotoId
getImageMetaDataByPhotoId

GET /api/v1/imagesharing/health
checkHealth

POST /api/v1/imagesharing/uploadImage
uploadImage



