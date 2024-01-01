import { NavLink } from "react-router-dom";
import CustomerModel from "../../../Models/CustomerModel";
import "./CustomerCard.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser } from "@fortawesome/free-solid-svg-icons";

interface CustomerCardProps {
    customer: CustomerModel;
}

function CustomerCard(props: CustomerCardProps): JSX.Element {
    return (
            <NavLink to={"/customers/details/" + props.customer.id}>
        <div className="CustomerCard Box">
                    <span id="customer_icon"><FontAwesomeIcon icon={faUser} /> {props.customer.id} </span>
                    <div>
                        Full Name: {props.customer?.firstName} {props.customer?.lastName}
                        <br />
                        Email: {props.customer?.email}
                    </div>
        </div>
            </NavLink>
    );
}

export default CustomerCard;
