import { NavLink } from "react-router-dom";
import "./Customer.css";

function Customer(): JSX.Element {
    return (
        <div className="Customer Box">
            <h2>What would you like to do?</h2>
            <NavLink id="coupons" to={"/customer/coupons"}>I want to view my coupons</NavLink>
            <NavLink id="purchase" to={"/coupons/"}>I want to buy a new coupon</NavLink>
        </div>
    );
}

export default Customer;
