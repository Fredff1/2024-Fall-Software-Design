from graphviz import Digraph

# Define the UML class diagram using Graphviz
uml = Digraph("Property Repair Management", format="png")
uml.attr(rankdir="TB", fontsize="12", ratio="fill")

# RepairRequest
uml.node("RepairRequest", '''RepairRequest
- id: int
- time: datetime
- description: string
- source: Enum[Phone, WeChat]
- reportedBy: string
- status: Enum[Pending, Dispatched, InProgress, Completed]
- evaluation: string
- feedbackTime: datetime
- activeDispatch: Dispatch
''', shape="record")

# Dispatch
uml.node("Dispatch", '''Dispatch
- id: int
- time: datetime
- dispatcher: string
- worker: Worker
- status: Enum[Active, Completed]
- priority: int
- failureReason: string
''', shape="record")

# Worker
uml.node("Worker", '''Worker
- id: int
- name: string
- skills: List[FaultType]
- isAvailable: bool
- tasks: List[Dispatch]
- activities: List[MaintenanceActivity]
''', shape="record")

# MaintenanceActivity
uml.node("MaintenanceActivity", '''MaintenanceActivity
- id: int
- startTime: datetime
- endTime: datetime
- content: string
- status: Enum[Incomplete, Complete]
- workHours: float
''', shape="record")

# FaultType
uml.node("FaultType", '''FaultType
- id: int
- name: string
''', shape="record")

# Complaint
uml.node("Complaint", '''Complaint
- id: int
- time: datetime
- content: string
- status: Enum[Open, Closed]
- submissionTime: datetime
- relatedWorkers: List[Worker]
- relatedDispatchers: List[Dispatcher]
''', shape="record")

# Relationships
uml.edge("RepairRequest", "Dispatch", "1..*")
uml.edge("Dispatch", "Worker", "1")
uml.edge("Worker", "MaintenanceActivity", "1..*")
uml.edge("RepairRequest", "MaintenanceActivity", "1..*")
uml.edge("RepairRequest", "Complaint", "0..*")
uml.edge("FaultType", "Worker", "1..*")
uml.edge("Complaint", "Worker", "0..*")
uml.edge("Complaint", "Dispatch", "0..*")

# Render the UML diagram
try:
    uml_filepath = "./test"
    uml.render(uml_filepath, format="png", cleanup=True)
    uml_filepath + ".png"
except Exception as e:
    print(e)
