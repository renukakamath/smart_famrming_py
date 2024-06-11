from flask import *
from database import *

public=Blueprint('public',__name__)

@public.route('/')
def index():

	return render_template("index.html")


@public.route('/login',methods=['get','post'])
def login():
	if "login" in request.form:
		uname=request.form['username']
		pwd=request.form['password']
		print(uname,pwd)
		q="select * from login where username='%s' and password='%s'"%(uname,pwd)
		res=select(q)
		if res:
			lid=res[0]['login_id']
			session['lid']=lid
			print(session['lid'])
			if res[0]['usertype']=="admin":
				return redirect(url_for('admin.admin_home'))
			elif res[0]['usertype']=="agricultureofficers":
				q="select * from agricultureofficers where login_id='%s'"%(session['lid'])
				r=select(q)
				if r:
					session['oid']=r[0]['officer_id']
					print('ssssssssssssssssss',session['oid'])
				return redirect(url_for('officer.officer_home'))
			elif res[0]['usertype']=="logistic":
				q="select * from logistic where login_id='%s'"%(session['lid'])
				r=select(q)
				if r:
					session['logistic_id']=r[0]['logistic_id']
					# print('ssssssssssssssssss',session['logistic'])
				return redirect(url_for('boy.logistic_view_dispatch_order'))
	return render_template("login.html")




@public.route('/logistic_registration',methods=['get','post'])
def logistic_registration():
	data={}
	if 'submit' in request.form:
		courier=request.form['courier']
		city=request.form['city']
		phone=request.form['phone']
		email=request.form['email']
		username=request.form['username']
		password=request.form['password']

		q="SELECT * FROM `login` WHERE `username`='%s' AND `password`='%s'"%(username,password)
		print(q)
		res=select(q)
		if res:
			flash("username and password already exist !")
			return redirect(url_for('public.logistic_registration'))
		else:
			q="INSERT INTO `login` VALUES (null,'%s','%s','logistic')"%(username,password)
			print(q)
			res=insert(q)

			q="INSERT INTO `logistic` VALUES(NULL,'%s','%s','%s','%s','%s')"%(res,courier,city,phone,email)
			print(q)
			insert(q)

	return render_template('logistic_registration.html',data=data)
