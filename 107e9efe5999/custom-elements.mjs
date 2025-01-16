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

function show(element) {
  element.style.display = null
}

const tddDecision = document.getElementById("tdd-decision")
if (tddDecision) {
  const content = tddDecision.querySelector(".content")
  const notYetAcceptedDialog = tddDecision.querySelector(".not-yet-accepted-dialog")
  const previouslyAcceptedDialog = tddDecision.querySelector(".previously-accepted-dialog")
  if (content && notYetAcceptedDialog && previouslyAcceptedDialog) {

    const key = "tdd-decision"
    const previouslyAccepted = localStorage.getItem(key) === "yes"
    if (previouslyAccepted) {
      show(previouslyAcceptedDialog)

    } else {
      const checkbox = notYetAcceptedDialog.querySelector("input[type=checkbox]")
      checkbox.addEventListener("change", () => {
        if (checkbox.checked) {
          localStorage.setItem(key, "yes")
          content.classList.remove("hidden")
          notYetAcceptedDialog.classList.add("accepted")
          setTimeout(() => {
            notYetAcceptedDialog.classList.add("unstuck")
          }, 5000)
        }
      })
      show(notYetAcceptedDialog)
      content.classList.add("hidden")
    }
  }
}
