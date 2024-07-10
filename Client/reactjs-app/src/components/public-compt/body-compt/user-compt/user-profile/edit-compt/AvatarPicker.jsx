import { useEffect, useState } from 'react';
import { Button, Box } from '@mui/material';
import ImageCropper from './ImageCropper';


const AvatarPicker = ({ uploadAvatar, active }) => {
    const [selectedImage, setSelectedImage] = useState(null);
    const [croppedImage, setCroppedImage] = useState(null);

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setSelectedImage(reader.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const handleCropComplete = (croppedImage) => {
        setCroppedImage(croppedImage);
    };

    useEffect(() => {
        uploadAvatar(croppedImage)
    },[active])

    return (<>
        <Box p={2}>
            <input
                accept="image/*"
                style={{ display: 'none' }}
                id="raised-button-file"
                type="file"
                onChange={handleFileChange}
            />
            <label htmlFor="raised-button-file">
                <Button variant="contained" component="span">
                    Chọn tệp
                </Button>
            </label>
            {selectedImage && (
                <ImageCropper imageSrc={selectedImage} onCropComplete={handleCropComplete} />
            )}
        </Box>
    </>)
}

export default AvatarPicker