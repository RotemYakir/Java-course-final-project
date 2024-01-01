
class Config {

}

class DevelopmentConfig extends Config {
    loginUrl = "http://localhost:8080/login/"
  
    adminUrl = "http://localhost:8080/api/admin/"
    adminAddCompanyUrl = "http://localhost:8080/api/admin/add-company/"
    adminUpdateCompanyUrl = "http://localhost:8080/api/admin/update-company/"
    adminAddCustomerUrl = "http://localhost:8080/api/admin/add-customer/"
    adminUpdateCustomerUrl = "http://localhost:8080/api/admin/update-customer/"
    adminGetCompanyUrl = "http://localhost:8080/api/admin/get-company/"
    adminGetCustomerUrl = "http://localhost:8080/api/admin/get-customer/"
    adminDeleteCompanyUrl = "http://localhost:8080/api/admin/delete-company/"
    adminDeleteCustomerUrl = "http://localhost:8080/api/admin/delete-customer/"
    adminGetAllCompaniesUrl = "http://localhost:8080/api/admin/get-all-companies"
    adminGetAllCustomersUrl = "http://localhost:8080/api/admin/get-all-customers"
    
    companyUrl = "http://localhost:8080/api/company/"
    companyGetDetailsUrl = "http://localhost:8080/api/company/get-details"
    companyAddCouponUrl = "http://localhost:8080/api/company/add-coupon/"
    companyUpdateCouponUrl = "http://localhost:8080/api/company/update-coupon/"
    companyDeleteCouponUrl = "http://localhost:8080/api/company/delete-coupon/"
    companyGetAllCouponsUrl = "http://localhost:8080/api/company/get-all-coupons"
    
    
    customerUrl = "http://localhost:8080/api/customer/"
    customerGetDetailsUrl = "http://localhost:8080/api/customer/get-details"
    customerPurchaseCouponUrl = "http://localhost:8080/api/customer/purchase-coupon/"
    customerGetCouponsUrl = "http://localhost:8080/api/customer/get-coupons"
    customerGetAllCouponsUrl = "http://localhost:8080/api/customer/get-all-coupons"
    
}

class ProductionConfig extends Config {
    loginUrl = "http://coupon-system/login/"
    
    adminUrl = "http://coupon-system/api/admin/"
    adminAddCompanyUrl = "http://coupon-system/api/admin/add-company/"
    adminUpdateCompanyUrl = "http://coupon-system/api/admin/update-company/"
    adminAddCustomerUrl = "http://coupon-system/api/admin/add-customer/"
    adminUpdateCustomerUrl = "http://coupon-system/api/admin/update-customer/"
    adminGetCompanyUrl = "http://coupon-system/api/admin/get-company/"
    adminGetCustomerUrl = "http://coupon-system/api/admin/get-customer/"
    adminDeleteCompanyUrl = "http://coupon-system/api/admin/delete-company/"
    adminDeleteCustomerUrl = "http://coupon-system/api/admin/delete-customer/"
    adminGetAllCompaniesUrl = "http://coupon-system/api/admin/get-all-companies"
    adminGetAllCustomersUrl = "http://coupon-system/api/admin/get-all-customers"
    
    companyUrl = "http://coupon-system/api/company/"
    companyGetDetailsUrl = "http://coupon-system/api/company/get-details"
    companyAddCouponUrl = "http://coupon-system/api/company/add-coupon/"
    companyUpdateCouponUrl = "http://coupon-system/api/company/update-coupon/"
    companyDeleteCouponUrl = "http://coupon-system/api/company/delete-coupon/"
    companyGetAllCouponsUrl = "http://coupon-system/api/company/get-all-coupons"
    
    customerUrl = "http://coupon-system/api/customer/"
    customerGetDetailsUrl = "http://coupon-system/api/customer/get-details"
    customerPurchaseCouponUrl = "http://coupon-system/api/customer/purchase-coupon/"
    customerGetCouponsUrl = "http://coupon-system/api/customer/get-coupons"
    customerGetAllCouponsUrl = "http://coupon-system/api/customer/get-all-coupons"
}

const appConfig = process.env.NODE_ENV === "development" ? new DevelopmentConfig() : new ProductionConfig();
export default appConfig;