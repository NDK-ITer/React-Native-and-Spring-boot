export const createImage = (url) =>
    new Promise((resolve, reject) => {
        const image = new Image();
        image.addEventListener('load', () => resolve(image));
        image.addEventListener('error', (error) => reject(error));
        image.setAttribute('crossOrigin', 'anonymous'); // Để tránh các vấn đề CORS
        image.src = url;
    });

export const getRadianAngle = (degreeValue) => {
    return (degreeValue * Math.PI) / 180;
};
