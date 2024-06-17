const tableBody = document.querySelector("#pacientesTable tbody");

function fetchPacientes() {
  fetch(`/paciente`)
   .then(response => response.json())
   .then(data => {
      console.log(data);
      tableBody.innerHTML = "";
      data.forEach((paciente, index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${index + 1}</td>
          <td>${paciente.apellido}</td>
          <td>${paciente.nombre}</td>
          <td>${paciente.dni}</td>
          <td>${paciente.fechaIngreso}</td>
          <td>${paciente.domicilio.calle}, ${paciente.domicilio.numero}, ${paciente.domicilio.localidad}, ${paciente.domicilio.provincia}</td>
          <td>
            <button class="btn btn-primary btn-sm" onclick="editPaciente(${paciente.id}, '${paciente.nombre}', '${paciente.apellido}', '${paciente.dni}', '${paciente.fechaIngreso}', ${JSON.stringify(paciente.domicilio)})">Modificar</button>
            <button class="btn btn-danger btn-sm" onclick="deletePaciente(${paciente.id})">Eliminar</button>
          </td>
        `;
        tableBody.appendChild(row);
      });
    })
   .catch(error => {
      console.error("Error fetching data:", error);
    });
}

function editPaciente(id, nombre, apellido, dni, fechaIngreso, domicilio) {
  document.getElementById("editId").value = id;
  document.getElementById("editNombre").value = nombre;
  document.getElementById("editApellido").value = apellido;
  document.getElementById("editDni").value = dni;
  document.getElementById("editFechaIngreso").value = fechaIngreso;
  document.getElementById("editCalle").value = domicilio.calle;
  document.getElementById("editNumero").value = domicilio.numero;
  document.getElementById("editLocalidad").value = domicilio.localidad;
  document.getElementById("editProvincia").value = domicilio.provincia;
  new bootstrap.Modal(document.getElementById("editModal")).show();
}

document.getElementById("editForm")
 .addEventListener("submit", function(event) {
    event.preventDefault();
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

    fetch(`/paciente`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(pacienteData),
    })
     .then(response => response.json())
     .then(data => {
        console.log(data);
        alert("Paciente modificado con éxito");
        fetchPacientes();
        bootstrap.Modal.getInstance(document.getElementById("editModal")).hide();
      })
     .catch(error => {
        console.error("Error editando paciente:", error);
      });
  });

function deletePaciente(id) {
  if (confirm("¿Estás seguro de que deseas eliminar este paciente?")) {
    fetch(`/paciente/${id}`, {
      method: "DELETE",
    })
     .then((response) => response.json())
     .then(data => {
        console.log(data);
        alert("Paciente eliminado con éxito");
        fetchPacientes();
      })
     .catch(error => {
        console.error("Error eliminando paciente:", error);
      });
  }
}

fetchPacientes();