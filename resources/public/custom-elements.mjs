const exerciseSchedule = document.getElementById("exercise-schedule")
if (exerciseSchedule) {
  for (const checkbox of document.querySelectorAll("input[data-schedule-step]")) {
    const key = "exercise-schedule/" + checkbox.getAttribute("data-schedule-step")
    checkbox.checked = localStorage.getItem(key) === "true"
    checkbox.addEventListener("change", () => {
      localStorage.setItem(key, "" + checkbox.checked)
    })
  }
}
