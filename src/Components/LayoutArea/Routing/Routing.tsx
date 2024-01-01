import { Navigate, Route, Routes } from "react-router-dom";
import "./Routing.css";
import Home from "../../HomeArea/Home/Home";
import Admin from "../../HomeArea/Admin/Admin";
import Company from "../../HomeArea/Company/Company";
import Customer from "../../HomeArea/Customer/Customer";
import PageNotFound from "../PageNotFound/PageNotFound";
import Login from "../../AuthArea/Login/Login";
import Logout from "../../AuthArea/Logout/Logout";
import AddCustomer from "../../AdminArea/AddCustomer/AddCustomer";
import AddCoupon from "../../CompanyArea/AddCoupon/AddCoupon";
import UpdateCoupon from "../../CompanyArea/UpdateCoupon/UpdateCoupon";
import CouponsListCompany from "../../CompanyArea/CouponsListCompany/CouponsListCompany";
import CouponDetails from "../../CardsArea/CouponDetails/CouponDetails";
import AddCompany from "../../AdminArea/AddCompany/AddCompany";
import EditCompany from "../../AdminArea/EditCompany/EditCompany";
import EditCustomer from "../../AdminArea/EditCustomer/EditCustomer";
import CompaniesList from "../../AdminArea/CompaniesList/CompaniesList";
import CouponsListCustomer from "../../CustomerArea/CouponsListCustomer/CouponsListCustomer";
import CouponsList from "../../CouponsArea/CouponsList/CouponsList";
import CompanyDetails from "../../CardsArea/CompanyDetails/CompanyDetails";
import CustomersList from "../../AdminArea/CustomersList/CustomersList";
import CustomerDetails from "../../CardsArea/CustomerDetails/CustomerDetails";
import PriceFilter from "../../CouponsArea/PriceFilter/PriceFilter";
import CategoryFilteredCoupons from "../../CouponsArea/CategoryFilteredCoupons/CategoryFilteredCoupons";
import CategoryFilteredCompany from "../../CompanyArea/CategoryFilteredCompany/CategoryFilteredCompany";
import CategoryFilteredCustomer from "../../CustomerArea/CategoryFilteredCustomer/CategoryFilteredCustomer";
import CompanyPriceFilter from "../../CompanyArea/CompanyPriceFilter/CompanyPriceFilter";
import CustomerPriceFilter from "../../CustomerArea/CustomerPriceFilter/CustomerPriceFilter";
import About from "../About/About";

function Routing(): JSX.Element {
    return (
        <div className="Routing">
            <Routes>
                <Route path="/home" element={<Home />} />
                <Route path="/about" element={<About />} />

                <Route path="/admin" element={<Admin />} />
                <Route path="/admin/companies/" element={<CompaniesList />} />
                <Route path="/admin/customers/" element={<CustomersList />} />
                <Route path="/admin/customers/new" element={<AddCustomer />} />
                <Route path="/admin/customers/edit/:customerId" element={<EditCustomer />} />
                <Route path="/admin/companies/new" element={<AddCompany />} />
                <Route path="/admin/companies/edit/:companyId" element={<EditCompany />} />

                <Route path="/companies/details/:companyId" element={<CompanyDetails />} />
                <Route path="/customers/details/:customerId" element={<CustomerDetails />} />
                <Route path="/coupons/details/:couponId" element={<CouponDetails />} />

                <Route path="/company" element={<Company />} />
                <Route path="/company/coupons" element={<CouponsListCompany />} />
                <Route path="/company/coupons/:category" element={<CategoryFilteredCompany />} />
                <Route path="/company/coupons/to-price/:price" element={<CompanyPriceFilter />} />
                <Route path="/company/coupons/new" element={<AddCoupon />} />
                <Route path="/company/coupons/edit/:couponId" element={<UpdateCoupon />} />

                <Route path="/customer" element={<Customer />} />
                <Route path="/customer/coupons/" element={<CouponsListCustomer />} />
                <Route path="/customer/coupons/:category" element={<CategoryFilteredCustomer />} />
                <Route path="/coupons/" element={<CouponsList />} />
                <Route path="/coupons/:category" element={<CategoryFilteredCoupons />} />
                <Route path="/coupons/to-price/:price" element={<PriceFilter />} />
                <Route path="/customer/coupons/to-price/:price" element={<CustomerPriceFilter />} />

                <Route path="/login" element={<Login />} />
                <Route path="/logout" element={<Logout />} />
                <Route path="" element={<Navigate to={"/home"} />} />
                <Route path="*" element={<PageNotFound />} />
            </Routes>
        </div>
    );
}

export default Routing;
