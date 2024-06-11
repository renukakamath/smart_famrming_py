from flask import *
from database import *
import uuid

officer=Blueprint('officer',__name__)

@officer.route('/officer_home')
def officer_home():
	return render_template("officer_home.html")


@officer.route('/reg_farmer')
def reg_farmer():
	data={}
	q="select * from farmers"
	res=select(q)
	data['farmers']=res
	return render_template("reg_farmer.html",data=data)

@officer.route('/tutorials',methods=['get','post'])
def tutorials():
	if "tutorials" in request.form:
		title=request.form['title']
		des=request.form['des']
		i=request.files['path']
		path="static/"+str(uuid.uuid4())+i.filename
		i.save(path)
		ftype=request.form['ftype']
		q="insert into tutorials values(null,'%s','%s','%s','%s','%s')"%(session['oid'],title,des,path,ftype)
		insert(q)

	data={}
	q="select * from tutorials"
	res=select(q)
	data['tutorials']=res

	if 'action' in request.args:
		action=request.args['action']
		tid=request.args['tid']
	else:
		action=None
	if action=="delete":
		q="delete from tutorials where  tutorial_id='%s'"%(tid)
		delete(q)
		return redirect(url_for('officer.tutorials'))

	if action=='update':
		q="select * from tutorials where  tutorial_id='%s'"%(tid)
		res=select(q)
		data['res']=res

	if 'edit' in request.form:
		title=request.form['title']	
		des=request.form['des']
		i=request.files['path']
		path="static/"+str(uuid.uuid4())+i.filename
		i.save(path)
		ftype=request.form['ftype']
		
		q="update tutorials set title='%s',description='%s',file_path='%s',file_type='%s' where tutorial_id='%s'"%(title,des,path,ftype,tid)
		update(q)
		
		return redirect(url_for('officer.tutorials'))



	return render_template("tutorials.html",data=data)

@officer.route('/products',methods=['get','post'])
def products():
	if "products" in request.form:
		pname=request.form['pname']
		amt=request.form['amt']
		pdes=request.form['pdes']
		q="insert into products values(null,'%s','%s','%s')"%(pname,amt,pdes)
		insert(q)
	data={}
	q="select * from products"
	res=select(q)
	data['products']=res

	if 'action' in request.args:
		action=request.args['action']
		pid=request.args['pid']
	else:
		action=None
	if action=="delete":
		q="delete from products where  product_id='%s'"%(pid)
		delete(q)
		return redirect(url_for('officer.products'))

	if action=='update':
		q="select * from products where  product_id='%s'"%(pid)
		res=select(q)
		data['res']=res

	if 'edit' in request.form:
		pname=request.form['pname']	
		amt=request.form['amt']
		pdes=request.form['pdes']
		

		q="update products set product='%s',amount='%s',description='%s' where product_id='%s'"%(pname,amt,pdes,pid)
		update(q)
		
		return redirect(url_for('officer.products'))
	return render_template("products.html",data=data)

@officer.route('/enquiry',methods=['post','get'])
def enquiry():
	data={}
	q="SELECT * FROM `enquiry` INNER JOIN `farmers` USING(farmer_id)"
	res=select(q)
	data['enquiry']=res

	if 'id' in request.args:
		id=request.args['id']
		des=request.args['des']
		data['des']=des

	if 'reply' in request.form:
		txt=request.form['txt']
		q="update enquiry set reply='%s' where enquiry_id='%s'"%(txt,id)
		update(q)
		flash("reply updated.....")
		return redirect(url_for("officer.enquiry"))

	return render_template("enquiry.html",data=data)

@officer.route('/booking',methods=['post','get'])
def booking():
	data={}
	q="SELECT * FROM `booking` INNER JOIN `products` USING(product_id)INNER JOIN `farmers` USING(farmer_id)"
	res=select(q)
	data['booking']=res

	return render_template("booking.html",data=data)

@officer.route('/officer_payment')
def officer_payment():
	data={}
	bid=request.args['bid']
	q="select * from payment where booking_id='%s'"%(bid)
	print(q)
	res=select(q)
	data['payment']=res

	return render_template('officer_payment.html',data=data)