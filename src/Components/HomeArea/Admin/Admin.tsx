import { NavLink } from "react-router-dom";
import "./Admin.css";

function Admin(): JSX.Element {
    return (
        <div className="Admin Box">
            <h2>What would you like to do?</h2>
            <NavLink id="add_comp" to={"/admin/companies/new"}>I want to add a new company</NavLink>
            <NavLink id="companies" to={"/admin/companies/"}>I want to view and edit my companies list</NavLink>
            <NavLink id="add_cust" to={"/admin/customers/new"}>I want to add a new customer</NavLink>
            <NavLink id="customers" to={"/admin/customers/"}>I want to view and edit my customers list</NavLink>
        </div>
    );
}

export default Admin;
