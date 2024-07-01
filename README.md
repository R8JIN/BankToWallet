# Bank To Wallet

### Database Configuration
```
CREATE DATABASE DIGITALWALLET;
CREATE USER 'root'@'localhost' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON digital_wallet.* TO 'root'@'localhost';
```

### Change Directory to digitalWallet
```
cd digitalWallet
```

### Installation
```shell
mvnw clean install
```

### Run the Spring Boot App
```shell
mvnw spring-boot:run
```

### Change Directory to tempalte
```shell
cd template
```

### Installation
```shell
mvnw clean install
```

### Run the Spring Boot App
```shell
mvnw spring-boot:run
```

## Docker
For docker add the following command
Change directory to template and run the docker image of template
```shell
cd template
docker-compose up build
```
To run the docker image of database
```shell
docker run --name my-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=digitalWallet -d mysql:latest
docker exec -it my-mysql bash
mysql -u root -p
```
## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.
