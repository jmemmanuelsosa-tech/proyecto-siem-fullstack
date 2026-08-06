# Importamos las librerias
import requests
import pandas as pd

# Definir las variables de conexión
URL_LOGS = "http://localhost:8081/api/logs"
# Copiá un JWT válido desde el localStorage del navegador para las pruebas
TOKEN_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbW1hbnVlbHNvc2FwIiwiaWF0IjoxNzg2MDMxOTcyLCJleHAiOjE3ODYwNjc5NzJ9.qLYpvgLUa-w2cmpwO3TcDWXcnT2aoYt9fNu1T-dUZUM" 

headers = {
    "Authorization": f"Bearer {TOKEN_JWT}"
}

# Consultar la API y convertir en DataFrame
respuesta = requests.get(URL_LOGS, headers=headers)

if respuesta.status_code == 200:
    logs = respuesta.json()
    df = pd.DataFrame(logs)
    print(df.columns)
    
    # 1. Total de eventos
    total_filas = len(df)
    
    # 2. Contar fallos (Asegurate que la columna en el DF sea 'resultado')
    logins_fallidos = (df['exito'] == 'Fallo').sum()
    
    # 3. Evaluar nivel de amenaza
    if logins_fallidos == 0:
        nivel_amenaza = "BAJA"
    elif logins_fallidos <= 3:
        nivel_amenaza = "MEDIA"
    else:
        nivel_amenaza = "ALTA"
        
    print(f"Total eventos: {total_filas}")
    print(f"Logins fallidos: {logins_fallidos}")
    print(f"Amenazas detectadas: {nivel_amenaza}")

else:
    print(f"Error {respuesta.status_code}: {respuesta.text}")
