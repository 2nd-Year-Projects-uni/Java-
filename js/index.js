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

//JS — SCROLL REVEAL
document.addEventListener("DOMContentLoaded", () => {
    const items = document.querySelectorAll('.reveal-item');

    const observer = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                let delay = entry.target.getAttribute("data-delay") || "0s";
                entry.target.style.animationDelay = delay;
                entry.target.classList.add("show");
            }
        });
    }, { threshold: 0.2 });

    items.forEach(el => observer.observe(el));
});

// MULTI–IMAGE MOUSE TRACKING FOR 3 WORDS
document.querySelectorAll(".sig-row").forEach(row => {

    const img = row.querySelector(".sig-moving-img");

    row.addEventListener("mousemove", e => {
        const rect = row.getBoundingClientRect();
        const x = e.clientX - rect.left;

        img.style.left = `${x}px`;
        img.style.opacity = "1";
        img.style.transform = "translateX(-50%) scale(1)";
    });

    row.addEventListener("mouseleave", () => {
        img.style.opacity = "0";
        img.style.transform = "translateX(-50%) scale(0.4)";
    });
});



