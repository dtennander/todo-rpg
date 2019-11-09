import React, { useEffect, useState } from "react";

const Lists = ({ toggleDialog }) => {
  return (
    <div>
      <div>
        <button onClick={() => toggleDialog()}>Create list</button>
      </div>
      <div>TodoLists</div>
    </div>
  );
};

export default Lists;
