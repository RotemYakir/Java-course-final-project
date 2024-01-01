import { useEffect, useState } from "react";
import "./CompanyDetails.css";
import CompanyModel from "../../../Models/CompanyModel";
import { useNavigate, useParams } from "react-router-dom";
import adminService from "../../../Services/AdminService";
import notificationService from "../../../Services/NotificationService";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBuilding, faCircleArrowLeft, faFilePen, faTrashCan } from "@fortawesome/free-solid-svg-icons";
import { NavLink } from "react-router-dom";

function CompanyDetails(): JSX.Element {

    const params = useParams();
    const companyId = +params.companyId;
    const navigate = useNavigate();

    const [company, setCompany] = useState<CompanyModel>();

    useEffect(() => {
        adminService.getCompany(companyId)
            .then((c) => { setCompany(c) }).
            catch((err) => { notificationService.error(err) });
    }, []);

    async function deleteCompany() {
        try {
            if (window.confirm("Are you sure you want to delete this company?")) {
                await adminService.deleteCompany(companyId);
                notificationService.success("the company was deleted");
                navigate("/admin/companies/");
            }
        } catch (err: any) {
            notificationService.error(err);
        }
    }

    return (
        <div className="CompanyDetails Box">

            <NavLink id="delete" to={""} onClick={deleteCompany}>Delete <FontAwesomeIcon icon={faTrashCan} style={{ color: "#190b0b", }} /></NavLink>
            <NavLink id="edit" to={"/admin/companies/edit/" + companyId}>Edit  <FontAwesomeIcon icon={faFilePen} /></NavLink>
            <NavLink id="back" to={"/admin/companies/"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back </NavLink>
            <div id="details">
                <span>Name:</span> {company?.name}<br />
                <span>Email:</span> {company?.email}<br />
                <span>Password:</span> {company?.password}<br />
            </div>
            <div id="company_icon"><FontAwesomeIcon icon={faBuilding} /> {company?.id} </div>
        </div>
    );
}

export default CompanyDetails;
