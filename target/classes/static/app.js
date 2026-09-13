const CAMPOS_POR_OPCION = {
	1: [
		{ id: "numero", label: "Número decimal", placeholder: "25" },
		{ id: "longitud", label: "Longitud en bits", placeholder: "8" }
	],
	2: [
		{ id: "binario", label: "Número binario", placeholder: "11001" }
	],
	3: [
		{ id: "numero", label: "Número decimal", placeholder: "255" },
		{ id: "longitud", label: "Longitud en dígitos hex", placeholder: "2" }
	],
	4: [
		{ id: "hex", label: "Número hexadecimal", placeholder: "1A3" }
	],
	5: [
		{ id: "binario", label: "Número binario", placeholder: "1001" },
		{ id: "longitud", label: "Longitud en dígitos hex", placeholder: "2" }
	],
	6: [
		{ id: "hex", label: "Número hexadecimal", placeholder: "A3" }
	]
};

const selOpcion = document.getElementById("opcion");
const camposDiv = document.getElementById("campos");
const btn = document.getElementById("btnConvertir");
const resDiv = document.getElementById("resultado");

function renderCampos() {
	const opcion = Number.parseInt(selOpcion.value, 10);
	const campos = CAMPOS_POR_OPCION[opcion] || [];
	camposDiv.replaceChildren();

	campos.forEach(campo => {
		const label = document.createElement("label");
		label.textContent = campo.label;
		label.setAttribute("for", campo.id);

		const input = document.createElement("input");
		input.id = campo.id;
		input.name = campo.id;
		input.placeholder = campo.placeholder;
		input.required = true;

		camposDiv.append(label, input);
	});
}

async function convertir(event) {
	event.preventDefault();

	const opcion = Number.parseInt(selOpcion.value, 10);
	const campos = CAMPOS_POR_OPCION[opcion] || [];
	const parametros = campos.map(campo => {
		return document.getElementById(campo.id).value.trim();
	});

	if (parametros.some(parametro => parametro === "")) {
		mostrarError("Completa todos los campos.");
		return;
	}

	ocultarResultado();

	try {
		const respuesta = await fetch("/api/convertir", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify({ opcion, parametros })
		});
		const data = await respuesta.json();

		if (!respuesta.ok || data.error || data.resultado == null) {
			mostrarError(data.error || "El servidor no devolvió un resultado.");
			return;
		}

		resDiv.textContent = "Resultado: " + data.resultado;
		resDiv.classList.remove("oculto", "error");
	} catch (error) {
		mostrarError("Error de conexión: " + error.message);
	}
}

function ocultarResultado() {
	resDiv.textContent = "";
	resDiv.classList.add("oculto");
	resDiv.classList.remove("error");
}

function mostrarError(mensaje) {
	resDiv.textContent = mensaje;
	resDiv.classList.remove("oculto");
	resDiv.classList.add("error");
}

selOpcion.addEventListener("change", renderCampos);
btn.addEventListener("click", convertir);
renderCampos();
