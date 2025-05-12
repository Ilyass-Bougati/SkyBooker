import json
import random
import requests
import threading

url = "http://localhost:8080/api/v1/auth/register"

with open('MOCK_CLIENT_DATA.json', 'r') as file:
    data = json.load(file)

def register(n: int, m: int):
    for client in data[n:m - 1]:
        client["cin"] = client["first_name"][0] + client["last_name"][0] + str(random.randint(10000, 99999))
        client["age"] = random.randint(1, 80)
        client["prenom"] = client["first_name"]
        client["nom"] = client["last_name"]
        client["telephone"] = client["phone"]

        x = requests.post(url, json=client)
        if x.status_code != 200:
            print(x.status_code)
            print(x.json())
            print(client)
            break

if __name__ == "__main__":
    threads = [
            threading.Thread(target=register, args=(i * 100, (i + 1) * 100)) for i in range(10)
        ]
    
    for t in threads:
        print(t.name)
        t.start()
    
    for t in threads:
        t.join()