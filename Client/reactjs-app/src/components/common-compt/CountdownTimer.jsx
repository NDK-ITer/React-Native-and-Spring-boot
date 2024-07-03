import React, { useEffect } from 'react';

function CountdownTimer({setState, setTimeCount, timeCount}) {
    
    useEffect(() => {
        // setSeconds(initialSeconds);
        if (timeCount > 0) {
            const timer = setInterval(() => {
                setTimeCount((prevSeconds) => prevSeconds - 1);
            }, 1000);
            return () => clearInterval(timer);
        }else{
            setState(true)
        }
    }, [timeCount, setState]);

    return (
        <div>
            {timeCount}s
        </div>
    );
}

export default CountdownTimer;
