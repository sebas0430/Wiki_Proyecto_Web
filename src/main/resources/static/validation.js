document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('contactForm');
    if (!form) return;

    const inputs = {
        nombre: document.getElementById('nombre'),
        email: document.getElementById('email'),
        telefono: document.getElementById('telefono'),
        asunto: document.getElementById('asunto'),
        mensaje: document.getElementById('mensaje')
    };

    const errorSpans = {
        nombre: document.getElementById('error-nombre'),
        email: document.getElementById('error-email'),
        telefono: document.getElementById('error-telefono'),
        asunto: document.getElementById('error-asunto'),
        mensaje: document.getElementById('error-mensaje')
    };

    // Character counter for textarea
    if (inputs.mensaje) {
        const charCount = document.getElementById('charCount');
        const charsNeeded = document.getElementById('charsNeeded');

        inputs.mensaje.addEventListener('input', function () {
            const currentLength = this.value.length;
            if (charCount) charCount.textContent = currentLength;

            if (currentLength < 10) {
                if (charsNeeded) charsNeeded.style.display = 'inline';
            } else {
                if (charsNeeded) charsNeeded.style.display = 'none';
            }
        });
    }

    form.addEventListener('submit', function (e) {
        let isValid = true;

        // Reset errors
        Object.values(errorSpans).forEach(span => {
            if (span) span.textContent = '';
        });

        // Validate Name
        if (!inputs.nombre.value.trim()) {
            showError('nombre', 'El nombre es obligatorio');
            isValid = false;
        }

        // Validate Email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!inputs.email.value.trim()) {
            showError('email', 'El correo es obligatorio');
            isValid = false;
        } else if (!emailRegex.test(inputs.email.value.trim())) {
            showError('email', 'Formato de correo inválido');
            isValid = false;
        }

        // Validate Phone (simple check)
        const phoneRegex = /^[0-9]+$/;
        if (!inputs.telefono.value.trim()) {
            showError('telefono', 'El teléfono es obligatorio');
            isValid = false;
        } else if (!phoneRegex.test(inputs.telefono.value.trim())) {
            showError('telefono', 'El teléfono solo debe contener números');
            isValid = false;
        }

        // Validate Subject
        if (!inputs.asunto.value) {
            showError('asunto', 'Seleccione un asunto');
            isValid = false;
        }

        // Validate Message
        if (!inputs.mensaje.value.trim()) {
            showError('mensaje', 'El mensaje es obligatorio');
            isValid = false;
        } else if (inputs.mensaje.value.trim().length < 10) {
            showError('mensaje', 'El mensaje debe tener al menos 10 caracteres');
            isValid = false;
        }

        if (!isValid) {
            e.preventDefault(); // Stop form submission
        }
    });

    function showError(field, message) {
        const span = errorSpans[field];
        if (span) {
            span.textContent = message;
            span.style.color = 'red';
            span.style.fontSize = '0.875rem';
        }
    }
});
