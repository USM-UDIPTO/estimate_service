FROM openjdk:15-alpine
COPY estimate-service.jar /deployments/estimate-service.jar
EXPOSE 8080
CMD java -Dspring.profiles.active=$PROFILE -jar /deployments/estimate-service.jar --DB_HOSTNAME=$DATABASE_SVC --DB_PORT=$DATABASE_PORT --DB_DATABASE=$DATABASE_NAME --DB_USER=$DATABASE_USER --DB_PASSWORD=$DATABASE_PASSWORD --kie-server-service.url=$KIE_SERVER/rest --pki-service.url=$PKI_SERVICE --supplier-service.url=$SUPPLIER_SERVICE  --amqphub.amqp10jms.remote-url=amqp://$JMS_SERVER:$JMS_SERVER_PORT --eprocurement.mail.base-url=http://$MAIL_IP:$MAIL_PORT/supplier/supplier-registration/verify-otp?key=
