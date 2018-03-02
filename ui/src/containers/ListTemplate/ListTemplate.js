import React from 'react'
import {connect} from "react-redux"
import {fetchTemplates} from "../../store/template/actions"
import moment from "moment";

import PropTypes from "prop-types"

class Template extends React.Component {

    static contextTypes = {
        router: PropTypes.shape({
            history: PropTypes.shape({
                push: PropTypes.func.isRequired,
                replace: PropTypes.func.isRequired
            }).isRequired,
            staticContext: PropTypes.object
        }).isRequired
    };

    componentDidMount() {
        const { dispatch } = this.props
        dispatch(fetchTemplates())
    }

    render() {

        const notFoundTemplates = (
            <tr>
                <td colSpan={ 2 }>Not found templates</td>
            </tr>
        )

        const rows = (this.props.templates.map((template, i) =>
            <tr style={{cursor: 'pointer'}}  key={i} onClick={ _ => this.context.router.history.push(`/template/${template.name}`) }>
                <td>{template.name}</td>
                <td>{moment(template.created_timestamp).format("MMMM Do YYYY, h:mm:ss a")}</td>
            </tr>
        ))

        return (
            <div>
                <button type="button" className="btn btn-success" onClick={ _ => this.context.router.history.push('/template/create')}>
                    New AWS Template
                </button>
                <br/>
                <br/>
                <table className="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Create date</th>
                    </tr>
                    </thead>
                    <tbody>
                        {this.props.templates.length > 0 && rows}
                        {this.props.templates.length <= 0 && notFoundTemplates}
                    </tbody>
                </table>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        templates: state.templates
    }
}

export default connect(mapStateToProps)(Template)

