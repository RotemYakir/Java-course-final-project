import CompanyModel from "../../../Models/CompanyModel";
import "./CompanyCard.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBuilding } from "@fortawesome/free-solid-svg-icons";
import { NavLink } from "react-router-dom";

interface CompanyCardProps {
    company: CompanyModel;
}

function CompanyCard(props: CompanyCardProps): JSX.Element {
    return (
        
        <NavLink to={"/companies/details/"+props.company.id}>
            <div className="CompanyCard Box">
                <span id="company_icon"><FontAwesomeIcon icon={faBuilding} /> {props.company.id} </span>
                <div>
                    Name: {props.company.name}
                    <br />
                    Email: {props.company.email}
                </div>

            </div>
        </NavLink>
    );
}

export default CompanyCard;
