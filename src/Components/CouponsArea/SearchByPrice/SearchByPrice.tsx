import { useForm } from "react-hook-form";
import "./SearchByPrice.css";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import notificationService from "../../../Services/NotificationService";

function SearchByPrice(): JSX.Element {

    const [inputValue, setInputValue] = useState<string>();
    const navigate = useNavigate();

    function handleInputChange(event: React.ChangeEvent<HTMLInputElement>) {
        const newValue = event.target.value;
        setInputValue(newValue);
    }

    function send() {
        if (+inputValue >= 0) {
            navigate("/coupons/to-price/" + inputValue);
          } else {
            notificationService.error("price cannot be negative!");
          }
    }

    return (
        <div className="SearchByPrice Box">
            Search coupons up to price: <input onChange={handleInputChange} type="number" placeholder="insert max price..." />
            <button onClick={send}>search</button>
        </div>
    );
}

export default SearchByPrice;
