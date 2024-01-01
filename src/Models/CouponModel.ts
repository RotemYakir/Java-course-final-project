import CompanyModel from "./CompanyModel";
import Category from "./Enums/Category";

class CouponModel{
id:number;
title:string;
description:string;
category:Category|string;
startDate: Date;
endDate:Date;
amount:number;
price:number;
image:string;
company:CompanyModel;
}



export default CouponModel;

