## CẤU HÌNH 

$env:DB_PASSWORD=""
$env:MAIL_USERNAME=""
$env:MAIL_PASSWORD=""
$env:ADMIN_PASSWORD=""
$env:CLOUDINARY_CLOUD_NAME=""
$env:CLOUDINARY_API_KEY=""
$env:CLOUDINARY_API_SECRET=""
$env:MAIL_PORT=""
$env:MAIL_HOST=""

## CHẠY CHƯƠNG TRÌNH

mvn clean package 

mvn spring-boot:run

## TRUY CẬP

http://localhost:8080