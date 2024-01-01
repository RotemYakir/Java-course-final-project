import { useNavigate, useParams } from "react-router-dom";
import "./CustomerDetails.css";
import CustomerModel from "../../../Models/CustomerModel";
import { useEffect, useState } from "react";
import adminService from "../../../Services/AdminService";
import notificationService from "../../../Services/NotificationService";
import { NavLink } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircleArrowLeft, faFilePen, faTrashCan, faUser } from "@fortawesome/free-solid-svg-icons";

function CustomerDetails(): JSX.Element {

    const params = useParams();
    const customerId = +params.customerId;
    const navigate = useNavigate();

    const [customer, setCustomer] = useState<CustomerModel>();

    useEffect(() => {
        adminService.getCustomer(customerId)
            .then((c) => { setCustomer(c) }).
            catch((err) => { notificationService.error(err) });
    }, []);

    async function deleteCustomer() {
        try {
            if (window.confirm("Are you sure you want to delete this customer?")) {
                await adminService.deleteCustomer(customerId);
                notificationService.success("the customer was deleted");
                navigate("/admin/customers/");
            }
        } catch (err: any) {
            notificationService.error(err);
        }
    }

    return (
        <div className="CustomerDetails Box">
            <NavLink id="delete" to={""} onClick={deleteCustomer}>Delete <FontAwesomeIcon icon={faTrashCan} style={{ color: "#190b0b", }} /></NavLink>
            <NavLink id="edit" to={"/admin/customers/edit/" + customerId}>Edit  <FontAwesomeIcon icon={faFilePen} /></NavLink>
            <NavLink id="back" to={"/admin/customers/"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back </NavLink>
            <div id="details">
                <span>Full Name:</span> {customer?.firstName} {customer?.lastName}<br />
                <span>Email:</span> {customer?.email}<br />
                <span>Password:</span> {customer?.password}<br />
            </div>
            <div id="customer_icon"><FontAwesomeIcon icon={faUser} /> {customer?.id} </div>
        </div>
    );
}

export default CustomerDetails;
