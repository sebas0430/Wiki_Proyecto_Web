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

    const feedback = document.getElementById('form-feedback');

    // Contador de caracteres para el textarea de mensaje
    if (inputs.mensaje) {
        const charCount = document.getElementById('charCount');
        const charsNeeded = document.getElementById('charsNeeded');

        inputs.mensaje.addEventListener('input', function () {
            const currentLength = this.value.length;
            if (charCount) {
                charCount.textContent = currentLength;
            }

            // Mostrar cuantos caracteres faltan para cumplir el minimo de 20
            if (currentLength < 20) {
                const faltan = 20 - currentLength;
                if (charsNeeded) {
                    charsNeeded.textContent = '(Faltan ' + faltan + ' caracteres)';
                    charsNeeded.style.display = 'inline';
                }
            } else if (currentLength > 400) {
                // Avisar si excede el maximo de 400
                if (charsNeeded) {
                    charsNeeded.textContent = '(Excede el límite por ' + (currentLength - 400) + ' caracteres)';
                    charsNeeded.style.display = 'inline';
                }
            } else {
                // Esta dentro del rango valido
                if (charsNeeded) {
                    charsNeeded.style.display = 'none';
                }
            }
        });
    }

    form.addEventListener('submit', function (e) {
        let isValid = true;

        // Limpiar errores anteriores
        for (const key in errorSpans) {
            if (errorSpans[key]) {
                errorSpans[key].textContent = '';
                errorSpans[key].style.display = 'none';
            }
        }

        // Quitar clases de error de los inputs
        for (const campo in inputs) {
            if (inputs[campo]) {
                inputs[campo].classList.remove('input-error');
            }
        }

        // Ocultar feedback anterior
        if (feedback) {
            feedback.style.display = 'none';
            feedback.classList.remove('success', 'error');
        }

        // =============================================
        // Validar Nombre: obligatorio, minimo 3 caracteres, no solo espacios
        // =============================================
        const nombreValue = inputs.nombre.value.trim();
        if (!nombreValue) {
            showError('nombre', 'El nombre es obligatorio');
            isValid = false;
        } else if (nombreValue.length < 3) {
            showError('nombre', 'El nombre debe tener al menos 3 caracteres');
            isValid = false;
        }

        // =============================================
        // Validar Email: obligatorio, formato valido con @ y punto
        // =============================================
        const emailValue = inputs.email.value.trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailValue) {
            showError('email', 'El correo electrónico es obligatorio');
            isValid = false;
        } else if (!emailRegex.test(emailValue)) {
            showError('email', 'Formato de correo inválido (debe incluir @ y un punto)');
            isValid = false;
        }

        // =============================================
        // Validar Telefono: obligatorio, solo numeros, minimo 7, maximo 15 digitos
        // =============================================
        const telefonoValue = inputs.telefono.value.trim();
        const phoneRegex = /^[0-9]+$/;
        if (!telefonoValue) {
            showError('telefono', 'El teléfono es obligatorio');
            isValid = false;
        } else if (!phoneRegex.test(telefonoValue)) {
            showError('telefono', 'El teléfono solo debe contener números');
            isValid = false;
        } else if (telefonoValue.length < 7) {
            showError('telefono', 'El teléfono debe tener al menos 7 dígitos');
            isValid = false;
        } else if (telefonoValue.length > 15) {
            showError('telefono', 'El teléfono no debe exceder 15 dígitos');
            isValid = false;
        }

        // =============================================
        // Validar Asunto: obligatorio, no valor por defecto
        // =============================================
        if (!inputs.asunto.value) {
            showError('asunto', 'Debe seleccionar un asunto');
            isValid = false;
        }

        // =============================================
        // Validar Mensaje: obligatorio, minimo 20 caracteres, maximo 400
        // =============================================
        const mensajeValue = inputs.mensaje.value.trim();
        if (!mensajeValue) {
            showError('mensaje', 'El mensaje es obligatorio');
            isValid = false;
        } else if (mensajeValue.length < 20) {
            const faltan = 20 - mensajeValue.length;
            showError('mensaje', 'El mensaje debe tener al menos 20 caracteres (faltan ' + faltan + ')');
            isValid = false;
        } else if (mensajeValue.length > 400) {
            showError('mensaje', 'El mensaje no debe exceder 400 caracteres');
            isValid = false;
        }

        // =============================================
        // Feedback al usuario
        // =============================================
        if (!isValid) {
            // Prevenir envio si hay errores
            e.preventDefault();
            if (feedback) {
                feedback.textContent = 'Por favor corrige los errores indicados antes de enviar.';
                feedback.classList.add('error');
                feedback.style.display = 'block';
            }
        } else {
            // Retroalimentacion positiva: formulario valido
            e.preventDefault();
            if (feedback) {
                feedback.textContent = '¡Formulario válido! Enviando información...';
                feedback.classList.add('success');
                feedback.style.display = 'block';
            }
            // Enviar el formulario despues de mostrar el mensaje positivo
            setTimeout(function () {
                form.submit();
            }, 1500);
        }
    });

    /**
     * Muestra un mensaje de error debajo del campo indicado
     * y aplica la clase visual de error al input
     */
    function showError(field, message) {
        const span = errorSpans[field];
        const input = inputs[field];
        if (span) {
            span.textContent = message;
            span.style.display = 'block';
        }
        if (input) {
            input.classList.add('input-error');
        }
    }
});
