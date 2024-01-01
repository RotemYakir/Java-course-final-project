import { NavLink } from "react-router-dom";
import CouponModel from "../../../Models/CouponModel";
import "./CouponCard.css";
import { authStore } from "../../../Redux/AuthState";
import ClientType from "../../../Models/Enums/ClientType";

interface CouponCardProps {
    coupon: CouponModel;
}

function CouponCard(props: CouponCardProps): JSX.Element {
    return (
        <NavLink to={"/coupons/details/" + props.coupon.id}>
            <div className="CouponCard Box">
                {props.coupon.title} <br />
                Price: {props.coupon.price}$ <br />
                {authStore.getState().user.clientType.toString()===ClientType[ClientType.COMPANY]&&<> Amount: {props.coupon.amount} <br /></>}
                {authStore.getState().user.clientType.toString()===ClientType[ClientType.CUSTOMER]&&<> Expires: {props.coupon.endDate} <br /></>}
                {props.coupon.image &&
                    <div>                        
                        <img src={props.coupon.image} alt="" />
                    </div>}
            </div>
        </NavLink>
    );
}

export default CouponCard;
