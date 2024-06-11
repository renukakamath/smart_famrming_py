from flask import *
from database import *
from knn import *
from predictweather import *
from samplecheck import *


admin=Blueprint('admin',__name__)

@admin.route('/admin_home')
def admin_home():
	return render_template("admin_home.html")


@admin.route('/officer',methods=['get','post'])
def officer():
	if "officer" in request.form:
		
		offname=request.form['offname']
		oflname=request.form['oflname']
		ofphone=request.form['ofphone']
		ofemail=request.form['ofemail']
		username=request.form['username']
		password=request.form['password']
		q="insert into login values(null,'%s','%s','agricultureofficers')"%(username,password)
		id=insert(q)


		q="insert into agricultureofficers values(null,'%s','%s','%s','%s','%s')"%(id,offname,oflname,ofphone,ofemail)
		insert(q)

	data={}
	q="select * from agricultureofficers"
	res=select(q)
	data['officer']=res

	if "action" in request.args:
		action=request.args['action']
		oid=request.args['oid']
	else:
		action=None
	if action=="delete":
		q="delete from agricultureofficers where officer_id='%s'"%(oid)
		delete(q)
		return redirect(url_for('admin.officer'))


	if action=='update':
		q="select * from agricultureofficers where  officer_id='%s'"%(oid)
		res=select(q)
		data['res']=res

	if 'edit' in request.form:
		offname=request.form['offname']	
		oflname=request.form['oflname']
		ofphone=request.form['ofphone']
		ofemail=request.form['ofemail']

		q="update agricultureofficers set first_name='%s',last_name='%s',phone='%s',email='%s' where officer_id='%s'"%(offname,oflname,ofphone,ofemail,oid)
		update(q)
		return redirect(url_for('admin.officer'))


	return render_template("officer.html",data=data)


@admin.route('/admin_manage_doctor',methods=['get','post'])
def admin_manage_doctor():
	
	if "officer" in request.form:
		
		offname=request.form['offname']
		oflname=request.form['oflname']
		ofqualifications=request.form['ofqualifications']
		ofphone=request.form['ofphone']
		ofemail=request.form['ofemail']
		username=request.form['username']
		password=request.form['password']
		q="insert into login values(null,'%s','%s','doctor')"%(username,password)
		id=insert(q)


		q="insert into doctors values(null,'%s','%s','%s','%s','%s','%s')"%(id,offname,oflname,ofqualifications,ofphone,ofemail)
		insert(q)

	data={}
	q="select * from doctors"
	res=select(q)
	data['officer']=res

	if "action" in request.args:
		action=request.args['action']
		oid=request.args['oid']
	else:
		action=None
	if action=="delete":
		q="delete from doctors where doctor_id='%s'"%(oid)
		delete(q)
		return redirect(url_for('admin.admin_manage_doctor'))


	if action=='update':
		q="select * from doctors where  doctor_id='%s'"%(oid)
		res=select(q)
		data['res']=res

	if 'edit' in request.form:
		offname=request.form['offname']	
		oflname=request.form['oflname']
		ofqualifications=request.form['ofqualifications']
		ofphone=request.form['ofphone']
		ofemail=request.form['ofemail']

		q="update doctors set first_name='%s',last_name='%s', qualification='%s' ,phone='%s' ,email='%s' where doctor_id='%s'"%(offname,oflname,ofqualifications,ofphone,ofemail,oid)
		update(q)
		return redirect(url_for('admin.admin_manage_doctor'))


	return render_template("admin_manage_doctor.html",data=data)

@admin.route('/news',methods=['get','post'])
def news():
	if "news" in request.form:
		title=request.form['title']
		des=request.form['des']
		q="insert into news values(null,'%s','%s',now())"%(title,des)
		insert(q)
	data={}
	q="select * from news"
	res=select(q)
	data['news']=res
	

	if 'action' in request.args:
		action=request.args['action']
		nid=request.args['nid']
	else:
		action=None
	if action=="delete":
		q="delete from news where news_id='%s'"%(nid)
		delete(q)
		return redirect(url_for('admin.news'))

	if action=='update':
		q="select * from news where  news_id='%s'"%(nid)
		res=select(q)
		data['res']=res

	if 'edit' in request.form:
		title=request.form['title']	
		des=request.form['des']
		

		q="update news set title='%s',description='%s' where news_id='%s'"%(title,des,nid)
		update(q)
		return redirect(url_for('admin.news'))	
	return render_template("news.html",data=data)






@admin.route('/farmers')
def farmers():
	data={}
	q="select * from farmers"
	res=select(q)
	data['farmers']=res
	return render_template("farmers.html",data=data)

@admin.route('/admin_view_users')
def admin_view_users():
	data={}
	q="select * from users"
	res=select(q)
	data['farmers']=res
	return render_template("admin_view_users.html",data=data)

@admin.route('/admin_view_product')
def admin_view_product():
	data={}
	q="SELECT * FROM `farmers` INNER JOIN `farmercrops`"
	res=select(q)
	data['view']=res
	return render_template("admin_view_product.html",data=data)


@admin.route('/croptype',methods=['get','post'])
def croptype():
	if "croptype" in request.form:
		ctype=request.form['ctype']
		cdes=request.form['cdes']
		q="insert into croptype values(null,'%s','%s')"%(ctype,cdes)
		insert(q)

	data={}
	q="select * from croptype"
	res=select(q)
	data['croptype']=res

	if "action" in request.args:
		action=request.args['action']
		tid=request.args['tid']
	else:
		action=None
	if action=="delete":
		q="delete from croptype where type_id='%s'"%(tid)
		delete(q)
		return redirect(url_for('admin.croptype'))


	if action=='update':
		q="select * from croptype where  type_id='%s'"%(tid)
		res=select(q)
		data['res']=res

	if 'edit' in request.form:
		ctype=request.form['ctype']	
		cdes=request.form['cdes']
		

		q="update croptype set type_name='%s',type_description='%s' where type_id='%s'"%(ctype,cdes,tid)
		update(q)
		return redirect(url_for('admin.croptype'))

	
	return render_template("croptype.html",data=data)

@admin.route('/complaint',methods=['post','get'])
def complaint():
	data={}
	q="SELECT * FROM `complaints` INNER JOIN `users` USING(user_id)"
	res=select(q)
	data['complaint']=res

	if 'id' in request.args:
		id=request.args['id']
		action=request.args['action']
		# data['com']=com
	else:
		action=None
	if action=='reply':
		com="aaa"
		data['com']=com

	if 'reply' in request.form:
		txt=request.form['txt']

		q="update complaints set reply='%s' where complaint_id='%s'"%(txt,id)
		update(q)
		flash("reply updated.....")
		return redirect(url_for("admin.complaint"))
	return render_template("complaint.html",data=data)


################################################################

def NeighborValues(ArrayValues, limit):
	global CROP_VALUE
	try:
		if limit == 3:
			pass
		else:
			new_crop_values = []
			for item in ArrayValues:
				new_crop_values.append(float(item) + 1)

			result = predict_farmer_crop(new_crop_values)

			if CROP_VALUE == result:
				pass
			else:
				CROP_VALUE = result
				ARRAY_PREDICTION.append(result)			

			NeighborValues(new_crop_values, len(ARRAY_PREDICTION))

			# print("new crop values", new_crop_values)
	except:
		pass
	pass

@admin.route("/cropyield",methods=['get','post'])
def cropyield():
	data={}

	q="SELECT * from state"
	print(q)
	res = select(q)
	data['state']=res

	#######state

	q="SELECT * from farmercrops "

	res1 = select(q)
	data['crops']=res1

	
	q="SELECT * from place "

	res2 = select(q)
	data['place']=res2

	

	

# 	###########crops

# 	val=[]

# 	if 'btn' in request.form:

# 		sid=request.form['sid']
# 		pid=request.form['pid']
# 		yy=request.form['yy']
# 		# y=request.args['year']
# 		cid=request.form['cid']
# 		acre=request.form['acre']
# 		import samplecheck 
# 		val.append(sid)
# 		val.append(pid)
# 		val.append(yy)
# 		# val.append(yy)
# 		val.append(cid)
# 		val.append(acre)
# 		print(val)
		
# 		res_set=samplecheck.predict_farmer_crop(val)
# 		print("dfbv",res_set)
# 		data['result'] = res_set
	

# 	# data['soil'] = ress[0]['soil_name']



# 	return render_template("admin_predictcropyield.html",data=data)






# # @api.route('/predcityield',methods=['get','post'])
# # def predcityield():
# # 	data={}
# # 	val=[]

# # 	sid=request.args['sid']
# # 	pid=request.args['pid']
# # 	yy=request.args['yy']
# # 	# y=request.args['year']
# # 	cid=request.args['cid']
# # 	acre=request.args['acre']
# # 	import samplecheck 
# # 	val.append(sid)
# # 	val.append(pid)
# # 	val.append(yy)
# # 	# val.append(yy)
# # 	val.append(cid)
# # 	val.append(acre)
# # 	print(val)
	
# # 	res_set=samplecheck.predict_farmer_crop(val)
# # 	print("dfbv",res_set)
# # 	data['result'] = res_set
	

# # 	# data['soil'] = ress[0]['soil_name']
# # 	data['status']  = 'success'
# # 	data['method']='predcityield'
# # 	return  demjson.encode(data)



# # @api.route('/viewplace',methods=['get','post'])
# # def viewplace():
# # 	data={}
# # 	sid=request.args['sid']
# # 	q="SELECT * from place where state_id='%s'" %(sid)
# # 	print(q)
# # 	res = select(q)
# # 	print(res)
# # 	if res :
# # 		data['status']  = 'success'
# # 		data['data'] = res
# # 	else:
# # 		data['status']	= 'failed'
# # 	data['method']='viewplace'
# # 	return  demjson.encode(data)

