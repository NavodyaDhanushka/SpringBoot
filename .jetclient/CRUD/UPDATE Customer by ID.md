```toml
name = 'UPDATE Customer by ID'
method = 'PUT'
url = 'http://localhost:8080/editcustomer/2'
sortWeight = 4000000
id = '9bf608b3-7760-4f67-973c-c6ee2195a22a'

[body]
type = 'JSON'
raw = '''
{
  
  "name": "John Doe",
  "nic": "991234567V",
  "contactNo": "+94798765432",
  "address": "456 Park Avenue, Colombo"
}
'''
```
