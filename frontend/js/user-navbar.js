document.addEventListener('DOMContentLoaded', function () {
    const navRight = document.querySelector('.nav-right');

    if (!navRight) return;

    const loginLink = navRight.querySelector('a[href="login.html"]');
    let sessionUser = null;
    try {
        sessionUser = JSON.parse(localStorage.getItem('userSession'));
    } catch { }

    if (sessionUser && sessionUser.name && sessionUser.email) {
        // Hide Login link if it exists
        if (loginLink) loginLink.style.display = 'none';

        // Create user icon
        let userIcon = document.createElement('span');
        userIcon.className = 'user-icon';
        userIcon.innerHTML = '<i class="fa-regular fa-user"></i>';
        navRight.appendChild(userIcon);

        // Create popup menu
        let popup = document.createElement('div');
        popup.className = 'user-popup';
        popup.innerHTML = `
      <div class="user-info">
        <strong>${sessionUser.name}</strong>
        <span class="user-email">${sessionUser.email}</span>
      </div>
      <button class="logout-btn">Logout</button>
    `;
        document.body.appendChild(popup);

        // Toggle popup logic
        userIcon.addEventListener('click', function (e) {
            e.stopPropagation();
            popup.style.display = popup.style.display === 'block' ? 'none' : 'block';
        });

        // Close popup when clicking outside
        document.addEventListener('click', function (e) {
            popup.style.display = 'none';
        });

        // Prevent closing when clicking inside popup
        popup.addEventListener('click', function (e) {
            e.stopPropagation();
        });

        // Logout logic
        popup.querySelector('.logout-btn').addEventListener('click', function () {
            localStorage.removeItem('userSession');
            window.location.href = 'index.html'; // Redirect to home on logout
        });
    }
});
