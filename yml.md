```yaml
// mail-properties.yml
spring:
    mail:
        host: {smtp name}
        port: {smtp port}
        username: { your email address }
        password: { your password}
        properties:
            mail.smtp.auth: true
            mail.smtp.ssl.enable: true
```

```yaml
// salt.yml
sat:
    key: {your salt key}
```