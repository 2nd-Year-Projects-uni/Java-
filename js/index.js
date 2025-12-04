document.addEventListener("DOMContentLoaded", function () {

    const words = ["Tailored", "Measured", "Crafted", "Styled"];
    const typedSpan = document.getElementById("typedWord");

    let wordIndex = 0;
    let charIndex = 0;
    let isDeleting = false;

    function typeEffect() {
        const currentWord = words[wordIndex];

        if (!isDeleting) {
            // Typing forward
            typedSpan.textContent = currentWord.substring(0, charIndex + 1);
            charIndex++;

            if (charIndex === currentWord.length) {
                setTimeout(() => (isDeleting = true), 1000); // wait before deleting
            }
        } else {
            // Deleting backward
            typedSpan.textContent = currentWord.substring(0, charIndex - 1);
            charIndex--;

            if (charIndex === 0) {
                isDeleting = false;
                wordIndex = (wordIndex + 1) % words.length; // move to next word
            }
        }

        setTimeout(typeEffect, isDeleting ? 200 : 150);
    }

    typeEffect();
});


