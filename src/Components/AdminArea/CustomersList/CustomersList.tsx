import { useEffect, useState } from "react";
import "./CustomersList.css";
import adminService from "../../../Services/AdminService";
import notificationService from "../../../Services/NotificationService";
import CustomerModel from "../../../Models/CustomerModel";
import CustomerCard from "../../CardsArea/CustomerCard/CustomerCard";
import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { NavLink } from "react-router-dom";


function CustomersList(): JSX.Element {

    const [customers, setCustomers] = useState<CustomerModel[]>([]);

    useEffect(() => {
        adminService.getAllCustomers()
            .then((arr) => { setCustomers(arr) })
            .catch((err) => { notificationService.error(err) });
    }, []);

    return (
        <div className="CustomersList">
            <h2>Here are all the customers:</h2>
            {customers.map((c) => (<CustomerCard key={c.id} customer={c} />))}
            <NavLink id="back" to={"/home"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back </NavLink>

        </div>
    );
}

export default CustomersList;
