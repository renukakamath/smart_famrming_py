from flask import *
from database import *

boy=Blueprint('boy',__name__)

@boy.route('/logistic_home')
def logistic_home():

	return render_template("logistic_home.html")

@boy.route('/logistic_view_dispatch_order',methods=['get','post'])
def logistic_view_dispatch_order():
	
	data={}
	q="SELECT * FROM `users`,`farmercrops`,`ordermaster`,`orderdetails` WHERE `users`.`user_id`=`ordermaster`.`user_id` AND `ordermaster`.`om_id`=`orderdetails`.`om_id` AND `orderdetails`.`crop_id`=`farmercrops`.`crop_id` GROUP BY `ordermaster`.`om_id`"
	res=select(q)
	data['view']=res

	if 'action' in request.args:
		action=request.args['action']
		# dil_id=request.args['dil_id']
		omid=request.args['omid']
	else:
		action=None

	if action=='accept':
		q="UPDATE `ordermaster` SET `oder_status`='pickup' WHERE `om_id`='%s'"%(omid)
		print(q)
		update(q)
		return redirect(url_for('boy.logistic_view_dispatch_order'))

	if action=='delivery':
		q="UPDATE `ordermaster` SET `oder_status`='delivered' WHERE `om_id`='%s'"%(omid)
		print(q)
		update(q)
		return redirect(url_for('boy.logistic_home'))
	return render_template('logistic_view_dispatch_order.html',data=data)