	
from flask import Flask
from public import public
from admin import admin
from officer import officer
from boy import boy
from api import api

app=Flask(__name__)

app.secret_key="abnn"
app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(officer,url_prefix='/officer')
app.register_blueprint(boy,url_prefix='/boy')
app.register_blueprint(api,url_prefix='/api')


app.run(debug=True,port=5456,host="0.0.0.0") 