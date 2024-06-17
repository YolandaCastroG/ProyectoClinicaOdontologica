const form = document.getElementById("agregarForm");

form.addEventListener("submit", function (event) {
  event.preventDefault();

        // Recopila los datos del formulario
        const pacienteData = {
            nombre: document.getElementById("nombre").value,
            apellido: document.getElementById("apellido").value,
            dni: document.getElementById("dni").value,
            fechaIngreso: document.getElementById("fechaIngreso").value,
            domicilio: {
              calle: document.getElementById("calle").value,
              numero: document.getElementById("numero").value,
              localidad: document.getElementById("localidad").value,
              provincia: document.getElementById("provincia").value,
            }
          };

        console.log(pacienteData);

    fetch(`/paciente`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
       body: JSON.stringify(pacienteData)
        })
        .then((response) => response.json())
            .then((data) => {
              console.log(data);
              alert("Paciente agregado con éxito");
              form.reset(); // Resetear el formulario
            })
            .catch((error) => {
              console.error("Error agregando paciente:", error);
            });
});
