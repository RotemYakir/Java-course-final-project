import { useEffect, useState } from "react";
import CompanyModel from "../../../Models/CompanyModel";
import "./CompaniesList.css";
import adminService from "../../../Services/AdminService";
import notificationService from "../../../Services/NotificationService";
import CompanyCard from "../../CardsArea/CompanyCard/CompanyCard";
import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { NavLink } from "react-router-dom";


function CompaniesList(): JSX.Element {

    const [companies, setcompanies] = useState<CompanyModel[]>([]);

    useEffect(() => {
        adminService.getAllCompanies()
            .then((arr) => { setcompanies(arr) })
            .catch((err) => { notificationService.error(err) });
    }, []);

    return (
        <div className="CompaniesList">
            <h2>Here are all the companies:</h2>
            {companies.map((c) => (<CompanyCard key={c.id} company={c} />))}
            <NavLink id="back" to={"/home"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back </NavLink>

        </div>
    );
}

export default CompaniesList;
