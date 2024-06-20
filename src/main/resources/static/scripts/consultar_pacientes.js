document.addEventListener("DOMContentLoaded", function() {
  const tableBody = document.querySelector("#pacientesTable tbody");

  function fetchPacientes() {
    fetch(`/paciente`)
      .then(response => response.json())
      .then(data => {
        console.log(data);
        tableBody.innerHTML = "";

        // Verificar si data es un array
        if (Array.isArray(data)) {
          // Insertar los datos en la tabla
          data.forEach((paciente, index) => {
            const row = document.createElement("tr");
            row.innerHTML = `
              <td>${index + 1}</td>
              <td>${paciente.apellido}</td>
              <td>${paciente.nombre}</td>
              <td>${paciente.dni}</td>
              <td>${paciente.fechaIngreso}</td>
              <td>${paciente.domicilio ? `${paciente.domicilio.calle}, ${paciente.domicilio.numero}, ${paciente.domicilio.localidad}, ${paciente.domicilio.provincia}` : ''}</td>
              <td>
                <button class="btn btn-primary btn-sm edit-btn" data-id="${paciente.id}" data-apellido="${paciente.apellido}" data-nombre="${paciente.nombre}" data-dni="${paciente.dni}" data-fecha-ingreso="${paciente.fechaIngreso}" data-domicilio='${JSON.stringify(paciente.domicilio || {})}'>Modificar</button>
                <button class="btn btn-danger btn-sm delete-btn" data-id="${paciente.id}">Eliminar</button>
              </td>
            `;
            tableBody.appendChild(row);
          });

          assignEventListeners();
        } else {
          console.log("No se encontraron pacientes.");
          tableBody.innerHTML = `<tr><td colspan="7">No hay pacientes registrados.</td></tr>`;
        }
      })
      .catch(error => {
        console.error("Error fetching data:", error);
      });
  }

  // Función para asignar eventos a los botones de editar y eliminar
  function assignEventListeners() {
    document.querySelectorAll(".edit-btn").forEach(button => {
      button.addEventListener("click", function() {
        const id = this.getAttribute("data-id");
        const apellido = this.getAttribute("data-apellido");
        const nombre = this.getAttribute("data-nombre");
        const dni = this.getAttribute("data-dni");
        const fechaIngreso = this.getAttribute("data-fecha-ingreso");
        const domicilio = this.getAttribute("data-domicilio");

        editPaciente(id, apellido, nombre, dni, fechaIngreso, domicilio);
      });
    });

    document.querySelectorAll(".delete-btn").forEach(button => {
      button.addEventListener("click", function() {
        const id = this.getAttribute("data-id");
        deletePaciente(id);
      });
    });
  }

  function editPaciente(id, apellido, nombre, dni, fechaIngreso, domicilio) {
    let parsedDomicilio;
    try {
      parsedDomicilio = JSON.parse(domicilio);
    } catch (e) {
      console.error("Error parsing domicilio:", e);
      return;
    }
    document.getElementById("editId").value = id;
    document.getElementById("editApellido").value = apellido;
    document.getElementById("editNombre").value = nombre;
    document.getElementById("editDni").value = dni;
    document.getElementById("editFechaIngreso").value = fechaIngreso;
    document.getElementById("editCalle").value = parsedDomicilio.calle || '';
    document.getElementById("editNumero").value = parsedDomicilio.numero || '';
    document.getElementById("editLocalidad").value = parsedDomicilio.localidad || '';
    document.getElementById("editProvincia").value = parsedDomicilio.provincia || '';
    new bootstrap.Modal(document.getElementById("editModal")).show();
  }

  document.getElementById("editForm").addEventListener("submit", function(event) {
    event.preventDefault();
    const pacienteData = {
      id: document.getElementById("editId").value,
      apellido: document.getElementById("editApellido").value,
      nombre: document.getElementById("editNombre").value,
      dni: document.getElementById("editDni").value,
      fechaIngreso: document.getElementById("editFechaIngreso").value,
      domicilio: {
        calle: document.getElementById("editCalle").value,
        numero: document.getElementById("editNumero").value,
        localidad: document.getElementById("editLocalidad").value,
        provincia: document.getElementById("editProvincia").value,
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
});
