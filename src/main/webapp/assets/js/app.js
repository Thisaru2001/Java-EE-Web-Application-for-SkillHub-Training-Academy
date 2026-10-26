document.addEventListener('DOMContentLoaded', function () {
    const togglePassword = document.querySelector('[data-toggle-password]');
    if (togglePassword) {
        togglePassword.addEventListener('click', function () {
            const input = document.getElementById('passwordInput');
            const icon = this.querySelector('i');
            if (input.type === 'password') {
                input.type = 'text';
                icon.classList.remove('bi-eye');
                icon.classList.add('bi-eye-slash');
            } else {
                input.type = 'password';
                icon.classList.remove('bi-eye-slash');
                icon.classList.add('bi-eye');
            }
        });
    }
});
