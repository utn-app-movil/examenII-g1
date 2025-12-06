package interfaces

import model.lau_DTOContainers

interface lau_OnItemClickListener {
        interface OnItemClickListener {
            fun onItemClicked(container: lau_DTOContainers)
            fun onAssignClicked(container: lau_DTOContainers)
            fun onReleaseClicked(container: lau_DTOContainers)

        }
}
