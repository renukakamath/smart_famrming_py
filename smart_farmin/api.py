from flask import *
from database import *
import uuid
from knn import *
import csv
import demjson
from predictweather import *
from samplecheck import *



import json
from web3 import Web3, HTTPProvider

# truffle development blockchain address
blockchain_address = ' http://127.0.0.1:9545'
# Client instance to interact with the blockchain
web3 = Web3(HTTPProvider(blockchain_address))
# Set the default account (so we don't need to set the "from" for every transaction call)
web3.eth.defaultAccount = web3.eth.accounts[0]
compiled_contract_path = 'C:/Users/91812/Desktop/New folder/smart_famrming/smart_famrming/smart_famrming/smart_farmin/node_modules/.bin/build/contracts/smartfarm.json'
# compiled_contract_path = 'F:/NGO/node_modules/.bin/build/contracts/medicines.json'
# Deployed contract address (see `migrate` command output: `contract address`)
deployed_contract_address = '0x01874BbDeD2d14Ec08310C3000bD36d9112Cf8A3'

filename='RF2.sav'

model=pickle.load(open(filename,'rb'))










api=Blueprint('api',__name__)




@api.route("/login")
def login():
	data={}

	uname=request.args['username']
	pwd=request.args['password']


	print(uname,pwd)
	q="select * from login where username='%s' and password='%s'"%(uname,pwd)
	res=select(q)
	if res:
		
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'
	return str(data)

@api.route("/user")
def user():
	data={}

	fname=request.args['fname']
	lname=request.args['lname']
	latitude=request.args['latitude']
	longitude=request.args['longitude']
	phone=request.args['phone']
	email=request.args['email']
	hname=request.args['housename']
	place=request.args['place']
	pincode=request.args['pincode']
	district=request.args['district']
	uname=request.args['username']
	pwd=request.args['password']

	q="insert into login values(null,'%s','%s','users')"%(uname,pwd)
	id=insert(q)

	q="insert into users values(null,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(id,fname,lname,latitude,longitude,phone,email,hname,place,pincode,district)
	insert(q)
	data['status']='success'
	return str(data)

@api.route("/farmer")
def farmer():
	data={}
	fname=request.args['fname']
	lname=request.args['lname']
	place=request.args['place']
	pincode=request.args['pincode']
	gender=request.args['gender']
	dob=request.args['dob']
	phone=request.args['phone']
	email=request.args['email']
	latitude=request.args['latitude']
	longitude=request.args['longitude']
	uname=request.args['username']
	pwd=request.args['password']

	q="insert into login values(null,'%s','%s','farmers')"%(uname,pwd)
	id=insert(q)

	q="insert into farmers values(null,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(id,fname,lname,place,pincode,gender,dob,phone,email,latitude,longitude)
	print(q)
	insert(q)
	data['status']='success'
	return str(data)

@api.route("/viewusers")
def viewusers():
	data={}
	q="select * from users"
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	return str(data)

@api.route("/viewnews")
def viewnews():
	data={}
	q="select * from news"
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	return str(data)


@api.route("/cropview")
def cropview():
	data={}
	q="select * from farmercrops"
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	return str(data)


@api.route("/viewtu")
def viewtu():
	data={}
	q="select * from tutorials inner join agricultureofficers using (officer_id)"
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	
	return str(data)

@api.route("/viewtools")
def viewtools():
	data={}
	q="select * from products"
	res=select(q)
	if res:
		data['method']='viewtools'
		data['status']="success"
		data['data']=res
	return str(data)
	
@api.route("/viewbooking")
def viewbooking():
	data={}
	fid=request.args['fid']
	q="SELECT * FROM `booking` INNER JOIN `products` USING(product_id) where farmer_id=(select farmer_id from farmers where login_id='%s')"%(fid)

	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	return str(data)


@api.route("/toolbookings")
def toolbookings():
	data={}
	product_id=request.args['product_id']
	login_id=request.args['login_id']
	amount=request.args['amount']
	

	q="insert into booking values(null,'%s',(select farmer_id from farmers where login_id='%s'),'%s',curdate(),'pending')"%(product_id,login_id,amount)
	print(q)
	insert(q)
	data['method']='toolbooking'
	data['status']='success'
	
	return str(data)


@api.route('/userbooking')
def userbooking():
	data={}
	lid=request.args['log_id']
	quantity=request.args['quantity']
	cid=request.args['pid']
	print("id......................................",lid)
	q="select * from farmercrops where crop_id='%s'"%(cid)
	res=select(q)

	total = int(res[0]['amount']) * int(quantity)

	q="update farmercrops set availability=availability-'%s' where crop_id='%s'"%(quantity,cid)
	update(q)

	q="select * from ordermaster where oder_status='pending' and user_id=(select user_id from users where login_id='%s') "%(lid)

	val=select(q)
	if val:
		oid=val[0]['om_id']
	else:
		q="insert into ordermaster values(null,curdate(),(select user_id from users where login_id='%s'),'0','pending')"%(lid)
		oid=insert(q)
	
	q="select * from orderdetails where crop_id='%s' and om_id='%s'"%(cid,oid)
	v=select(q)
	if v:
		q="update orderdetails set quantity=quantity+'%s' , amount=amount+'%s' where od_id='%s'"%(quantity,total,v[0]['od_id'])
		update(q)
	else:
		q="insert into orderdetails values(null,'%s','%s','%s','%s')"%(oid,cid,quantity,total)
		insert(q)
	
	q="update ordermaster set total_amount=total_amount+'%s' where 	om_id='%s'"%(total,oid)	

	m=update(q)

	if m:
		data['status']='success'
	else:
		data['status']='failed'

	 
	return str(data)

@api.route("/User_Complaint")
def User_Complaint():
	data={}
	lid=request.args['log_id']
	complaints=request.args['complaints']
	q="insert into complaints values(null,(select user_id from users where login_id='%s'),'%s','pending',curdate())"%(lid,complaints)
	print(q)
	res=insert(q)

	if res:
		data['status']='success'
	else:
		data['status']='failed'
	data['method']='User_Complaint'

	return str(data)
@api.route("/viewcomp")
def viewcomp():
	data={}
	lid=request.args['log_id']
	q="select * from complaints where user_id=(select user_id from users where login_id='%s')"%(lid)
	print(q)
	res=select(q)

	data['status']='success'
	data['method']='User_feedbak'
	data['data']=res

	return str(data)

@api.route('/myorders')
def myorders():
	data={}
	log_id=request.args['log_id']

	q="select * from ordermaster inner join orderdetails using (om_id)  INNER JOIN `farmercrops` USING (crop_id)  where user_id=(select user_id from users where login_id='%s')  and oder_status='pending'"%(log_id)
	# print(q)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	return str(data)


@api.route('/userviewmyorders')
def userviewmyorders():
	data={}
	log_id=request.args['log_id']

	q="select * from ordermaster inner join orderdetails using (om_id)  INNER JOIN `farmercrops` USING (crop_id)  where user_id=(select user_id from users where login_id='%s') and oder_status!='pending'"%(log_id)
	# print(q)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	return str(data)


@api.route('/makepayment')
def makepayment():
	data={}
	bid=request.args['omid']
	amount=request.args['amount']



	# q="select * from wallet where user_id='%s' and amount<'%s'"%(lid,amount)
	# res=select(q)
	# print(q)
	# if res:

	# 	data['status']='already'

	# else:



	import datetime
	d=datetime.datetime.now().strftime("%d-%m-%Y %H:%M:%S")
	with open(compiled_contract_path) as file:
		contract_json = json.load(file)  # load contract info as JSON
		contract_abi = contract_json['abi']  # fetch contract's abi - necessary to call its functions
		contract = web3.eth.contract(address=deployed_contract_address, abi=contract_abi)
		id=web3.eth.get_block_number()
	message = contract.functions.add_payment(id,int(bid),'farmer',amount,d).transact()



	q="insert into payment values(null, '%s','farmer','%s',curdate())"%(bid,amount)
	insert(q)


	# q="update wallet set amount=amount-'%s' where user_id='%s'"%(amt,lid)
	# update(q)


	q="update ordermaster set oder_status='Payment completed' where om_id='%s'"%(bid)
	update(q)
	print(q)


	# q="update booking set status='Payment Completed' where booking_id='%s'"%(bid)
	# update(q)

	data['status']='success'
	return str(data)

@api.route("/farmviewod")
def farmviewod():
	data={}
	fid=request.args['log_id']
	q="SELECT * FROM `ordermaster` INNER JOIN `orderdetails` USING (om_id) INNER JOIN `users` USING (user_id) INNER JOIN `farmercrops` USING (crop_id) INNER JOIN `farmers` USING (farmer_id) where farmer_id=(select farmer_id from farmers where login_id='%s')"%(fid)

	res=select(q)
	if res:
		data['method']='farmviewod'
		data['status']="success"
		data['data']=res
	else:
		data['method']='farmviewod'
		data['status']="failed"
	return str(data)


@api.route("/farmacceptorder")
def farmacceptorder():
	data={}
	fid=request.args['om_id']
	q="update ordermaster set oder_status='Verified by Farmer' where om_id='%s'"%(fid)
	print(q)
	res=update(q)

	if res:
		data['data']=res
		data['status']="success"
		data['method']='farmacceptorder'
	else:
		data['status']="failed"
		data['method']='farmacceptorder'
	return str(data)



@api.route("/viewcroptype")
def viewcroptype():
	data={}
	
	q="select * from croptype"
	res=select(q)

	if res:
		data['status']="success"
		data['data']=res
		data['method']='viewcroptype'
	else:
		data['status']="failed"
		data['method']='viewcroptype'
	return str(data)

@api.route('/farmeraddcrop',methods=['get','post'])
def farmeraddcrop():
	data={}
	image=request.files['image']
	img="static/images/"+str(uuid.uuid4())+image.filename
	image.save(img)
	type_id=request.form['type_id']
	crop=request.form['crop']
	quantity=request.form['quantity']
	description=request.form['description']
	amount=request.form['amount']
	log_id=request.form['log_id']

	q="insert into farmercrops values(null,'%s',(select farmer_id from farmers where login_id='%s'),'%s','%s','%s','%s','%s')"%(type_id,log_id,crop,quantity,description,img,amount)
	res=insert(q)
	print(q)
	if res:
		data['status']='success'
		data['method']='farmeraddcrop' 
	else:
		data['status']='failed'
		data['method']='farmeraddcrop'


	return str(data)


@api.route("/deliverysuccess")
def deliverysuccess():
	data={}
	fid=request.args['oid']
	q="update ordermaster set oder_status='dispatch' where om_id='%s'"%(fid)
	print(q)
	res=update(q)

	if res:
	
		data['status']="success"
	
	else:
		data['status']="failed"
	
	return str(data)


@api.route("/enqsent")
def enqsent():
	data={}
	lid=request.args['log_id']
	enquiry=request.args['enq']
	q="insert into enquiry values(null,(select farmer_id from farmers where login_id='%s'),'%s','pending',curdate())"%(lid,enquiry)
	res=insert(q)

	if res:
		
		data['status']="success"
		data['method']='enqsent'
	else:
		data['status']="failed"
		data['method']='enqsent'
	return str(data)

@api.route("/viewenq")
def viewenq():
	data={}
	lid=request.args['log_id']
	q="select * from enquiry where farmer_id=(select farmer_id from farmers where login_id='%s')"%(lid)
	# print(q)
	res=select(q)

	if res:
		data['status']="success"
		data['data']=res
		data['method']='viewenq'
	else:
		data['status']="failed"
		data['method']='viewenq'
	return str(data)



############################




@api.route('/viewstate',methods=['get','post'])
def viewstate():
	data={}
	q="SELECT * from state"
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='viewstate'
	return  demjson.encode(data)


@api.route('/viewcrop',methods=['get','post'])
def viewcrop():
	data={}
	q="SELECT * from farmercrops "
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='viewcrop'
	return  demjson.encode(data)

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




@api.route('/predcityield',methods=['get','post'])
def predcityield():
	data={}
	val=[]

	sid=request.args['sid']
	pid=request.args['pid']
	yy=request.args['yy']
	# y=request.args['year']
	cid=request.args['cid']
	acre=request.args['acre']
	import samplecheck 
	val.append(sid)
	val.append(pid)
	val.append(yy)
	# val.append(yy)
	val.append(cid)
	val.append(acre)
	print(val)
	
	res_set=samplecheck.predict_farmer_crop(val)
	print("dfbv",res_set)
	data['result'] = res_set
	

	# data['soil'] = ress[0]['soil_name']
	data['status']  = 'success'
	data['method']='predcityield'
	return  demjson.encode(data)



@api.route('/viewplace',methods=['get','post'])
def viewplace():
	data={}
	sid=request.args['sid']
	q="SELECT * from place where state_id='%s'" %(sid)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='viewplace'
	return  demjson.encode(data)




@api.route('/suggestcropss',methods=['get','post'])
def suggestcropss():
	data={}
	global CROP_VALUE, ARRAY_PREDICTION
	ARRAY_PREDICTION = []
	ARRAY_PREDICTION.clear()
	crop_values = []
	crop_values.clear()
	moisture_low = (float(request.args['mois']) * 10)
	crop_values.append(moisture_low)

	moisture_high = (float(request.args['mois']) * 10) + 10
	crop_values.append(moisture_high)

	phvalue_low = float(request.args['phv'])  - 2
	crop_values.append(phvalue_low)

	phvalue_high = float(request.args['phv']) + 4
	crop_values.append(phvalue_high)

	nitrogen_low = (float(request.args['ni']) / 100) - 1
	crop_values.append(nitrogen_low)

	nitrogen_high = (float(request.args['ni']) / 100) + 1
	crop_values.append(nitrogen_high)

	phosphorus_low = (float(request.args['ph']) / 100) - 1 
	crop_values.append(phosphorus_low)

	phosphorus_high = (float(request.args['ph']) / 100) + 1 
	crop_values.append(phosphorus_high)

	potassium_low = (float(request.args['pot']) / 100) - 1
	crop_values.append(potassium_low)

	potassium_high = (float(request.args['pot']) / 100) + 1
	crop_values.append(potassium_high)

	temp_high = 38
	crop_values.append(temp_high)

	temp_low = 20
	crop_values.append(temp_low)
	print("test values = ", crop_values)
	result=""
	result = newpredict_farmer_cropss(crop_values)
	CROP_VALUE=""
	ARRAY_PREDICTION.clear()
	CROP_VALUE = result
	print(CROP_VALUE)
	ARRAY_PREDICTION.append(result)			
	NeighborValues(crop_values, 1)
	print("ARRAY_PREDICTION=", ARRAY_PREDICTION)

	# result = predict_farmer_crop(crop_values)
	# data['result_value'] = ARRAY_PREDICTION
	# print("hh",result)
	data['result'] = ARRAY_PREDICTION[0]
	print("///////////////////////////////////////////",ARRAY_PREDICTION[0])
	

	# data['soil'] = ress[0]['soil_name']
	data['status']  = 'success'
	data['method']='suggestcropss'
	return  demjson.encode(data)



@api.route('/cropfertpredict',methods=['get','post'])
def cropfertpredict():
	data={}
	# val=[]

	city=request.args['place']
	city = city+" weather"
	
	l,t,i,w,p=weather(city)
	pp=str(p)
	ps=pp.replace("%","")
	print(pp)
	data['outl']="Location : "+l
	data['outw']="Weather : "+i
	data['outd']="Degree : "+w+"degree celcius"

	data['outp']="Perception : "+p
	if int(ps)>50:
		out="Not a Good Time for Fertilize"
	else:
		out="Good Time for Fertilize"
	data['out']=out
	

	# data['soil'] = ress[0]['soil_name']
	data['status']  = 'success'
	data['method']='cropfertpredict'
	return  demjson.encode(data)



# @api.route('/Addwallet')
# def Addwallet():
# 	data={}
# 	a=request.args['amount']

	
# 	login_id=request.args['login_id']

# 	q="select * from wallet where user_id='%s'"%(login_id)
# 	res=select(q)
# 	print(q)
# 	if res:
# 		q="update wallet set amount=amount+'%s' where user_id='%s'"%(a,login_id)
# 		update(q)
# 		print(q)
			
# 	else:


# 		import datetime
# 		d=datetime.datetime.now().strftime("%d-%m-%Y %H:%M:%S")
# 		with open(compiled_contract_path) as file:
# 			contract_json = json.load(file)  # load contract info as JSON
# 			contract_abi = contract_json['abi']  # fetch contract's abi - necessary to call its functions
# 			contract = web3.eth.contract(address=deployed_contract_address, abi=contract_abi)
# 			id=web3.eth.get_block_number()
# 		message = contract.functions.add_wallet(id,int(login_id),a).transact()


# 		q="insert into wallet values(null,'%s','%s')"%(login_id,a)
# 		insert(q)
# 	data['status']='success'
# 	data['method']='Addwallet'
# 	return str(data)

@api.route('/searchmedicine')
def searchmedicine():
	data={}
	search='%'+request.args['search']+'%'
	q="select * from farmercrops where  crop_name like '%s'"%(search)
	res=select(q)
	print(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']="failed"
	return str(data)
