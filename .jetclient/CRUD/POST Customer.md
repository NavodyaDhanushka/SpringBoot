```toml
name = 'POST Customer'
method = 'POST'
url = 'http://localhost:8080/newcustomer'
sortWeight = 1000000
id = '77015ffa-ebdf-4a98-ab71-610160fca8aa'

[body]
type = 'JSON'
raw = '''
{
    "name": "Jane Smith",
    "nic": "200012345678",
    "contactNo": "+94711223344",
    "address": "45 Galle Road, Galle"
  }'''
```
